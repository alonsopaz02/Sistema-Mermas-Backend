package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {
    // Se pueden agregar consultas personalizadas si es necesario
}
