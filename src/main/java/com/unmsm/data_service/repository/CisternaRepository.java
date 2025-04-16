package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.Cisterna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CisternaRepository extends JpaRepository<Cisterna, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
