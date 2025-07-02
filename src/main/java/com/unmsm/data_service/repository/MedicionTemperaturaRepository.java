package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.MedicionTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicionTemperaturaRepository extends JpaRepository<MedicionTemperatura, Long> {

    /** Búsqueda flexible (cualquier filtro es opcional). */
    @Query("""
          SELECT m FROM MedicionTemperatura m
          WHERE (:tanqueId     IS NULL OR m.tanque.id    = :tanqueId)
            AND (:fechaIni     IS NULL OR m.fecha        >= :fechaIni)
            AND (:fechaFin     IS NULL OR m.fecha        <= :fechaFin)
            AND (:tipoMedicion IS NULL OR m.tipoMedicion = :tipoMedicion)
          ORDER BY m.fecha
          """)
    List<MedicionTemperatura> buscar(@Param("tanqueId")     Long tanqueId,
                                     @Param("fechaIni")     LocalDateTime fechaIni,
                                     @Param("fechaFin")     LocalDateTime fechaFin,
                                     @Param("tipoMedicion") String tipoMedicion);
}
