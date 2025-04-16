package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Puedes agregar métodos personalizados si es necesario
}
