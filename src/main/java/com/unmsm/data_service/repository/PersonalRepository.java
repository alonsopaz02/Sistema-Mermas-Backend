// repository/PersonalRepository.java
package com.unmsm.data_service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalRepository extends CrudRepository<com.unmsm.data_service.model.MedicionInventario, Long> {

    /** Devuelve responsables únicos (ordenados) */
    @Query(value = """
        SELECT DISTINCT mi.responsable
        FROM medicion_inventario mi
        WHERE mi.responsable IS NOT NULL AND mi.responsable <> ''
        ORDER BY mi.responsable
    """, nativeQuery = true)
    List<String> responsables();
}
