// repository/HomeRepository.java
package com.unmsm.data_service.repository;

import com.unmsm.data_service.dto.HomeSummary;
import com.unmsm.data_service.dto.MonthValue;
import com.unmsm.data_service.model.Transporte;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;

@org.springframework.stereotype.Repository
public interface HomeRepository extends Repository<com.unmsm.data_service.model.Transporte, Long> {

    /* ---------- KPIs del tablero principal ---------- */
    @Query(value = """
        SELECT
          /* --- merma total (transporte + tanques) ---------------- */
          ROUND(tr.mermaTransporte + inv.mermaInventario, 2) AS mermaTotal,
          /* --- litros transportados ----------------------------- */
          tr.volTransportado         AS volumenTransportado,
          tr.cntTransportes          AS eventosTransporte,
          /* --- conteos simples ---------------------------------- */
          (SELECT COUNT(*) FROM cisterna              ) AS vehiculosActivos,
          (SELECT COUNT(*) FROM tanque                ) AS tanquesActivos,
          (SELECT COUNT(*) FROM estacion              ) AS estacionesActivas,
          (SELECT COUNT(DISTINCT responsable)
             FROM medicion_inventario
             WHERE responsable IS NOT NULL AND responsable <> ''
          ) AS conductoresActivos
        FROM
          /* sub-consulta transporte ----------------------------------- */
          ( SELECT
              COALESCE(SUM(t.volumen_cargado_60) - SUM(IFNULL(d.volumen_60,0)),0) AS mermaTransporte,
              COALESCE(SUM(t.volumen_cargado_60),0)                               AS volTransportado,
              COUNT(*)                                                           AS cntTransportes
            FROM transporte t
            LEFT JOIN descarga d ON d.transporte_id = t.id
          ) tr,
          /* sub-consulta inventario ------------------------------------ */
          ( SELECT COALESCE(SUM(merma_60),0) AS mermaInventario
            FROM vista_mermas_tanque
          ) inv
        """, nativeQuery = true)
    HomeSummary resumen();


    /* ---------- merma mensual (gráfico línea/área) ---------- */
    @Query(value = """
        SELECT DATE_FORMAT(x.fecha,'%Y-%m') AS mes,
               ROUND(SUM(x.merma), 2)       AS valor
        FROM (
          /* transporte */
          SELECT DATE(t.fecha)                                         AS fecha,
                 SUM(t.volumen_cargado_60) - SUM(IFNULL(d.volumen_60,0)) AS merma
          FROM transporte t
          LEFT JOIN descarga d ON d.transporte_id = t.id
          GROUP BY DATE(t.fecha)

          UNION ALL
          /* tanques */
          SELECT v.dia             AS fecha,
                 SUM(v.merma_60)   AS merma
          FROM vista_mermas_tanque v
          GROUP BY v.dia
        ) x
        GROUP BY mes
        ORDER BY mes
        """, nativeQuery = true)
    List<MonthValue> mermaMensual();
}
