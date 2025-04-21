package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.OtrasOperaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtrasOperacionesRepository extends JpaRepository<OtrasOperaciones, Long> {
}
