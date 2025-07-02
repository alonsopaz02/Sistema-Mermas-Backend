package com.unmsm.data_service.repository;

import com.unmsm.data_service.dto.MermaResumenView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MermaRepository extends JpaRepository<com.unmsm.data_service.model.Tanque, Long> {

    /**  Devuelve la merma diaria, filtrable por rango de fechas y, opcionalmente,
     *   por estación o tanque.  Se basa en la consulta que ya verificaste en SQL.  */
    @Query(value = """
        WITH inv AS (
            SELECT
                mi.tanque_id                               AS tanque_id,
                DATE(mi.fecha)                             AS dia,
                MAX(CASE WHEN mi.comentario LIKE '%inicial%' THEN mi.inventario_obs END) AS inicial_obs,
                MAX(CASE WHEN mi.comentario LIKE '%inicial%' THEN mi.inventario_60  END) AS inicial_60,
                MAX(CASE WHEN mi.comentario LIKE '%final%'   THEN mi.inventario_obs END) AS final_obs,
                MAX(CASE WHEN mi.comentario LIKE '%final%'   THEN mi.inventario_60  END) AS final_60
            FROM medicion_inventario mi
            GROUP BY mi.tanque_id, DATE(mi.fecha)
        ),
        otras AS (
            SELECT tanque_id, DATE(fecha) AS dia,
                   SUM(volumen_observado) AS otras_obs,
                   SUM(volumen_60)        AS otras_60
            FROM otras_operaciones
            GROUP BY tanque_id, DATE(fecha)
        ),
        descg AS (
            SELECT tanque_id, DATE(fecha) AS dia,
                   SUM(volumen_descargado) AS desc_obs,
                   SUM(volumen_60)         AS desc_60
            FROM descarga
            GROUP BY tanque_id, DATE(fecha)
        ),
        vent AS (
            SELECT tanque_id, DATE(fecha) AS dia,
                   SUM(volumen_vendido) AS vent_obs,
                   SUM(volumen_60)      AS vent_60
            FROM venta
            GROUP BY tanque_id, DATE(fecha)
        ),
        cons AS (
            SELECT tanque_id, DATE(fecha) AS dia,
                   SUM(volumen_consumido) AS cons_obs,
                   SUM(volumen_60)        AS cons_60
            FROM consumo_tanque
            GROUP BY tanque_id, DATE(fecha)
        )
        SELECT
            inv.dia                                         AS fecha,
            e.nombre                                        AS estacion,
            t.codigo                                        AS tanque,
            p.nombre                                        AS producto,

            inv.inicial_obs     AS inventarioInicialObs,
            inv.inicial_60      AS inventarioInicial60,
            inv.final_obs       AS inventarioFinalObs,
            inv.final_60        AS inventarioFinal60,

            COALESCE(cons.cons_obs ,0)   AS consumoObs,
            COALESCE(cons.cons_60  ,0)   AS consumo60,
            COALESCE(otras.otras_obs,0)  AS otrasOpsObs,
            COALESCE(otras.otras_60 ,0)  AS otrasOps60,
            COALESCE(descg.desc_obs ,0)  AS descargasObs,
            COALESCE(descg.desc_60  ,0)  AS descargas60,
            COALESCE(vent.vent_obs ,0)   AS ventasObs,
            COALESCE(vent.vent_60  ,0)   AS ventas60,

            ROUND(
                inv.final_obs
                - (inv.inicial_obs
                   + COALESCE(otras.otras_obs,0)
                   + COALESCE(descg.desc_obs,0)
                   - COALESCE(vent.vent_obs,0)
                   - COALESCE(cons.cons_obs,0)
                ), 2)                    AS mermaObs,

            ROUND(
                inv.final_60
                - (inv.inicial_60
                   + COALESCE(otras.otras_60,0)
                   + COALESCE(descg.desc_60,0)
                   - COALESCE(vent.vent_60,0)
                   - COALESCE(cons.cons_60,0)
                ), 2)                    AS merma60
        FROM inv
        JOIN tanque    t ON t.id = inv.tanque_id
        JOIN estacion  e ON e.id = t.estacion_id
        JOIN producto  p ON p.id = t.producto_id
        LEFT JOIN otras ON otras.tanque_id = inv.tanque_id AND otras.dia = inv.dia
        LEFT JOIN descg ON descg.tanque_id = inv.tanque_id AND descg.dia = inv.dia
        LEFT JOIN vent  ON vent.tanque_id  = inv.tanque_id AND vent.dia  = inv.dia
        LEFT JOIN cons  ON cons.tanque_id  = inv.tanque_id AND cons.dia  = inv.dia
        WHERE inv.final_obs IS NOT NULL
          AND inv.inicial_obs IS NOT NULL
          AND inv.dia BETWEEN :inicio AND :fin
          AND (:estacionId IS NULL OR e.id = :estacionId)
          AND (:tanqueId   IS NULL OR t.id = :tanqueId)
        ORDER BY inv.dia, e.nombre, t.codigo
        """, nativeQuery = true)
    List<MermaResumenView> findResumenMermas(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin,
            @Param("estacionId") Long estacionId,
            @Param("tanqueId") Long tanqueId);
}
