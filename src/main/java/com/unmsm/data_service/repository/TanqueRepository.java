package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.Tanque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TanqueRepository extends JpaRepository<Tanque, Long> {
}
