package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.ConsumoTanque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumoTanqueRepository extends JpaRepository<ConsumoTanque, Long> {
    // Aquí podrías agregar métodos personalizados de consulta si lo necesitas
}
