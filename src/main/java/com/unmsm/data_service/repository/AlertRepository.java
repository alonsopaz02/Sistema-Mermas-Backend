// repository/AlertRepository.java
package com.unmsm.data_service.repository;

import com.unmsm.data_service.dto.AlertView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;

@org.springframework.stereotype.Repository
public interface AlertRepository extends Repository<com.unmsm.data_service.model.Transporte, Long> {
    /* 1. Merma anómala por día ----------------------------------- */
    @Query(value = """
        WITH m AS (
          SELECT v.dia AS fecha,
                 SUM(v.merma_60) +
                 (SELECT COALESCE(SUM(t.volumen_cargado_60)-SUM(IFNULL(d.volumen_60,0)),0)
                    FROM transporte t
                    LEFT JOIN descarga d ON d.transporte_id=t.id
                    WHERE DATE(t.fecha)=v.dia) AS mermaDia
          FROM vista_mermas_tanque v
          GROUP BY v.dia
        )
        SELECT ROW_NUMBER() OVER ()         AS id,
               'MERMA_DIA'                  AS tipo,
               'Merma anómala'              AS titulo,
               CONCAT('Merma de ', ROUND(mermaDia,2),' L el ', fecha) AS detalle,
               fecha                        AS fecha,
               mermaDia                     AS valor
        FROM m
        WHERE ABS(mermaDia) >
              400
        ORDER BY fecha DESC
        LIMIT 10
        """, nativeQuery = true)
    List<AlertView> mermaDia();
    /* 2. Temperaturas extremas ------------------------------------ */
    @Query(value = """
        SELECT ROW_NUMBER() OVER () AS id,
               'TEMP_EXTREMA'       AS tipo,
               'Temperatura extrema'AS titulo,
               CONCAT('Tanque ', ta.codigo,
                      ' a ', ROUND(mt.temperatura,1),' °C el ',
                      DATE(mt.fecha))            AS detalle,
               DATE(mt.fecha)        AS fecha,
               mt.temperatura        AS valor
        FROM medicion_temperatura mt
        JOIN tanque ta ON ta.id = mt.tanque_id
        WHERE mt.temperatura NOT BETWEEN 40 AND 50   -- rango seguro ejemplo
        ORDER BY mt.fecha DESC
        LIMIT 10
        """, nativeQuery = true)
    List<AlertView> temperatura();

    /* 3. Merma por transporte ------------------------------------- */
    @Query(value = """
        WITH x AS (
          SELECT t.id,
                 DATE(t.fecha)                            AS fecha,
                 (t.volumen_cargado_60-IFNULL(d.volumen_60,0)) AS merma,
                 c.placa                                  AS cisterna
          FROM transporte t
          JOIN cisterna c ON c.id=t.cisterna_id
          LEFT JOIN descarga d ON d.transporte_id=t.id
        )
        SELECT ROW_NUMBER() OVER () AS id,
               'MERMA_TRANS'        AS tipo,
               'Merma en transporte'AS titulo,
               CONCAT('Cisterna ', cisterna,
                      ' merma ', ROUND(merma,2),' L (',fecha,')') AS detalle,
               fecha                 AS fecha,
               merma                 AS valor
        FROM x
        WHERE ABS(merma) >
              (SELECT AVG(ABS(merma))+STDDEV(ABS(merma))*2 FROM x)
        ORDER BY fecha DESC
        LIMIT 10
        """, nativeQuery = true)
    List<AlertView> mermaTransporte();
}
