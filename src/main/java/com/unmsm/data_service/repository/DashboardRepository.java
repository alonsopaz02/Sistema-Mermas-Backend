package com.unmsm.data_service.repository;

import com.unmsm.data_service.dto.*;
import com.unmsm.data_service.model.Transporte;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repositorio “sólo lectura” para el Dashboard.
 * Todas las consultas son nativas y devuelven interfaces-proyección,
 * lo que evita los problemas de conversión Tuple→record.
 */
@org.springframework.stereotype.Repository
public interface DashboardRepository extends Repository<Transporte, Long> {

    /* ───────────────────────── 1. KPIs ───────────────────────── */

    @Query(value = """
        SELECT
          COALESCE(SUM(t.volumen_cargado_60) - SUM(d.volumen_60), 0)               AS mermaTransporte,
          COALESCE((SELECT SUM(merma_60) FROM vista_mermas_tanque), 0)             AS mermaTanques,
          COALESCE(SUM(t.volumen_cargado_60), 0)                                   AS volumenTransportado,
          COALESCE((
            SELECT AVG(sub.prom)
            FROM (
              SELECT DATE(mt.fecha)       AS dia,
                     AVG(mt.temperatura)  AS prom
              FROM medicion_temperatura mt
              GROUP BY DATE(mt.fecha)
              ORDER BY dia DESC
              LIMIT 7
            ) sub
          ), 0)                                                                   AS temperaturaProm7d
        FROM transporte t
        LEFT JOIN descarga d ON d.transporte_id = t.id
        """, nativeQuery = true)
    KpiView kpis();


    /* ─────────────────── 2. Merma diaria por transporte ─────────────────── */

    @Query(value = """
        SELECT DATE(t.fecha) AS label,
               COALESCE(SUM(t.volumen_cargado_60) - SUM(d.volumen_60), 0) AS value
        FROM transporte t
        LEFT JOIN descarga d ON d.transporte_id = t.id
        GROUP BY DATE(t.fecha)
        ORDER BY DATE(t.fecha)
        """, nativeQuery = true)
    List<PointView> mermaDiariaTransporte();


    /* ─────────────────── 3. Pie: merma por tipo ─────────────────── */

    @Query(value = """
        SELECT 'Transporte' AS name,
               COALESCE(SUM(t.volumen_cargado_60) - SUM(d.volumen_60), 0) AS value
        FROM transporte t
        LEFT JOIN descarga d ON d.transporte_id = t.id
        UNION ALL
        SELECT 'Tanques',
               COALESCE((SELECT SUM(merma_60) FROM vista_mermas_tanque), 0)
        """, nativeQuery = true)
    List<NameValueView> mermaPorTipo();


    /* ─────────────────── 4. Top 5 tanques con mayor merma ─────────────────── */

    @Query(value = """
        SELECT CONCAT('Tanque ', ta.codigo) AS name,
               COALESCE(SUM(v.merma_60), 0) AS value
        FROM vista_mermas_tanque v
        JOIN tanque ta ON ta.id = v.tanque_id
        GROUP BY ta.id, ta.codigo
        ORDER BY value DESC
        LIMIT 5
        """, nativeQuery = true)
    List<NameValueView> tanquesTop();


    /* ─────────────────── 5. Resumen de cisternas ─────────────────── */

    @Query(value = """
        SELECT c.placa,
               COALESCE(SUM(t.volumen_cargado_60), 0)                     AS volumen,
               COALESCE(SUM(t.volumen_cargado_60) - SUM(d.volumen_60), 0) AS merma,
               COALESCE(
                   ROUND(100 * (SUM(t.volumen_cargado_60) - SUM(d.volumen_60))
                         / NULLIF(SUM(t.volumen_cargado_60), 0), 2), 0)   AS porcentaje
        FROM transporte t
        JOIN cisterna c       ON c.id = t.cisterna_id
        LEFT JOIN descarga d  ON d.transporte_id = t.id
        GROUP BY c.id, c.placa
        ORDER BY merma DESC
        LIMIT 5
        """, nativeQuery = true)
    List<CisternaResumenView> cisternasResumen();


    /* ─────────────────── 6. Temperatura diaria (30 fechas más recientes) ─────────────────── */

    @Query(value = """
        SELECT dia AS label, val AS value
        FROM (
          SELECT DATE(mt.fecha) AS dia,
                 AVG(mt.temperatura) AS val
          FROM medicion_temperatura mt
          GROUP BY DATE(mt.fecha)
          ORDER BY dia DESC
          LIMIT 30
        ) t
        ORDER BY dia
        """, nativeQuery = true)
    List<PointView> temperaturaDiaria();


    /* ─────────────────── 7. Distribución de rangos de temperatura ─────────────────── */

    @Query(value = """
        SELECT rango AS name, COUNT(*) AS value
        FROM (
          SELECT CASE
                   WHEN temperatura < 40 THEN '< 40°C'
                   WHEN temperatura BETWEEN 40 AND 50 THEN '40–50°C'
                   ELSE '> 50°C'
                 END AS rango
          FROM medicion_temperatura
        ) x
        GROUP BY rango
        """, nativeQuery = true)
    List<NameValueView> temperaturaRangos();


    /* ─────────────────── 8. Inventario observado vs 60° ─────────────────── */

    @Query(value = """
        SELECT fecha, observado, a60
        FROM (
          SELECT DATE(mi.fecha)      AS fecha,
                 AVG(mi.inventario_obs) AS observado,
                 AVG(mi.inventario_60)  AS a60
          FROM medicion_inventario mi
          GROUP BY DATE(mi.fecha)
          ORDER BY fecha DESC
          LIMIT 15
        ) y
        ORDER BY fecha
        """, nativeQuery = true)
    List<InvComparativoView> invComparativo();

    /* ───────── 9. Ranking dinámico de operadores (merma en TANQUES) ───────── */
    @Query(value = """
    /*  ligamos la merma calculada por día + tanque con la medición FINAL,
        de la cual sacamos el responsable  */
    SELECT mi.responsable                        AS name,
           ROUND(AVG(v.merma_60), 2)             AS value      /* ⇦ promedio de merma */
    FROM vista_mermas_tanque v
    JOIN medicion_inventario mi
         ON mi.tanque_id = v.tanque_id
        AND DATE(mi.fecha)  = v.dia
        AND mi.comentario LIKE '%final%'          /* solo la medición final del día */
    WHERE mi.responsable IS NOT NULL
    GROUP BY mi.responsable
    ORDER BY value DESC
    """, nativeQuery = true)
    List<OperatorRankingView> rankingOperadores();

    /* ───────── 10. Waterfall diario (últimos 15 días con datos) ───────── */
    /* ───────── 10. Inventario TOTAL diario (últimos 30 cierres) ───────── */
    @Query(value = """
    SELECT DATE(mi.fecha) AS label,
           SUM(mi.inventario_obs) AS value
    FROM medicion_inventario mi
    WHERE mi.comentario LIKE '%final%'
    GROUP BY DATE(mi.fecha)
    ORDER BY DATE(mi.fecha) DESC
    LIMIT 30
    """, nativeQuery = true)
    List<PointView> inventarioDiarioTotal();


}
