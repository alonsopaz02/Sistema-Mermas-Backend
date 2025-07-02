package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.MedicionInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicionInventarioRepository extends JpaRepository<MedicionInventario, Long> {

    @Query("""
      SELECT m FROM MedicionInventario m
      WHERE (:tanqueId   IS NULL OR m.tanque.id = :tanqueId)
        AND (:fechaIni   IS NULL OR m.fecha >= :fechaIni)
        AND (:fechaFin   IS NULL OR m.fecha <= :fechaFin)
        AND (:comentario IS NULL OR LOWER(m.comentario) LIKE LOWER(CONCAT('%', :comentario, '%')))
      ORDER BY m.fecha
      """)
    List<MedicionInventario> buscar(@Param("tanqueId")   Long tanqueId,
                                    @Param("fechaIni")   LocalDateTime fechaIni,
                                    @Param("fechaFin")   LocalDateTime fechaFin,
                                    @Param("comentario") String comentario);
}
