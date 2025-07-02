// repository/ReportRepository.java
package com.unmsm.data_service.repository;

import com.unmsm.data_service.dto.rows.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface ReportRepository extends Repository<com.unmsm.data_service.model.Transporte, Long>
{
    /* 1 ▪ Merma en transporte --------------------------------------- */
    @Query(value = """
      SELECT DATE(t.fecha) AS fecha,
             c.placa       AS cisterna,
             ROUND(SUM(t.volumen_cargado_60) - SUM(IFNULL(d.volumen_60,0)),2) AS mermaL
      FROM transporte t
      JOIN cisterna c ON c.id = t.cisterna_id
      LEFT JOIN descarga d ON d.transporte_id = t.id
      WHERE DATE(t.fecha) BETWEEN :ini AND :fin
        AND (:cisternaId IS NULL OR c.id = :cisternaId)
      GROUP BY DATE(t.fecha), c.placa
      ORDER BY DATE(t.fecha)
    """, nativeQuery = true)
    List<MermaTransporteRow> mermaTransporte(String ini, String fin, Long cisternaId);

    /* 2 ▪ Merma en inventarios -------------------------------------- */
    @Query(value = """
      SELECT v.dia                         AS dia,
             CONCAT('TQ-', ta.codigo)      AS tanque,
             ROUND(SUM(v.merma_60),2)      AS merma60
      FROM vista_mermas_tanque v
      JOIN tanque ta ON ta.id = v.tanque_id
      WHERE v.dia BETWEEN :ini AND :fin
        AND (:tanqueId IS NULL OR ta.id = :tanqueId)
        AND (:estacionId IS NULL OR ta.estacion_id = :estacionId)
      GROUP BY v.dia, ta.codigo
      ORDER BY v.dia
    """, nativeQuery = true)
    List<MermaInventarioRow> mermaInventario(String ini, String fin,
                                             Long estacionId, Long tanqueId);

    /* 3 ▪ Operaciones (ventas, descargas, consumo, otras) ------------ */
    @Query(value = """
      SELECT x.fecha,
             x.tipo,
             x.entidad,
             x.volumen60
      FROM (
        /* -------- Descargas -------- */
        SELECT DATE(d.fecha)                 AS fecha,
               'DESCARGA'                    AS tipo,
               CONCAT('Provien de la Cisterna: ', ci.placa)      AS entidad,
               d.volumen_60                  AS volumen60
        FROM descarga d
        JOIN transporte t ON t.id = d.transporte_id
        JOIN cisterna   ci ON ci.id = t.cisterna_id
        WHERE DATE(d.fecha) BETWEEN :ini AND :fin

        UNION ALL
        /* -------- Ventas ----------- */
        SELECT DATE(v.fecha),
               'VENTA',
               CONCAT('Vendido al cliente: ', v.cliente) ,
               v.volumen_60
        FROM venta v
        WHERE DATE(v.fecha) BETWEEN :ini AND :fin

        UNION ALL
        /* -------- Consumo ---------- */
        SELECT DATE(ct.fecha),
               'CONSUMO',
               ct.motivo_consumo,
               ct.volumen_60
        FROM consumo_tanque ct
        WHERE DATE(ct.fecha) BETWEEN :ini AND :fin

        UNION ALL
        /* -------- Otras op. -------- */
        SELECT DATE(o.fecha),
               'OTRA',
               o.descripcion,
               o.volumen_60
        FROM otras_operaciones o
        WHERE DATE(o.fecha) BETWEEN :ini AND :fin
      ) x
      ORDER BY x.fecha
    """, nativeQuery = true)
    List<OperacionRow> operaciones(String ini, String fin);

    /* 4 ▪ Temperatura de tanques ------------------------------------ */
    @Query(value = """
      SELECT DATE(mt.fecha)        AS fecha,
             CONCAT('TQ-', ta.codigo) AS tanque,
             mt.temperatura
      FROM medicion_temperatura mt
      JOIN tanque ta ON ta.id = mt.tanque_id
      WHERE DATE(mt.fecha) BETWEEN :ini AND :fin
        AND (:tanqueId IS NULL OR ta.id = :tanqueId)
        AND (:estacionId IS NULL OR ta.estacion_id = :estacionId)
      ORDER BY DATE(mt.fecha)
    """, nativeQuery = true)
    List<TemperaturaRow> temperaturas(String ini, String fin,
                                      Long estacionId, Long tanqueId);

    /* 5 ▪ Merma por responsable ------------------------------------- */
    @Query(value = """
      SELECT mi.responsable                AS responsable,
             ROUND(AVG(v.merma_60),2)      AS mermaPromedio60
      FROM vista_mermas_tanque v
      JOIN medicion_inventario mi
           ON mi.tanque_id = v.tanque_id
          AND DATE(mi.fecha) = v.dia
          AND mi.comentario LIKE '%final%'
      WHERE v.dia BETWEEN :ini AND :fin
        AND (:responsable IS NULL OR mi.responsable = :responsable)
      GROUP BY mi.responsable
      ORDER BY mermaPromedio60 DESC
    """, nativeQuery = true)
    List<ResponsableRow> mermaPorResponsable(String ini, String fin, String responsable);
}
