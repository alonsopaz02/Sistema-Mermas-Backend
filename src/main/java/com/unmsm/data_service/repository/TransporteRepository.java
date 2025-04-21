package com.unmsm.data_service.repository;

import com.unmsm.data_service.model.Transporte;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {

    // Consulta personalizada para obtener el número de transportes por fecha
    @Query("SELECT DATE_FORMAT(t.fecha, '%Y-%m') AS mes, COUNT(t) AS totalTransportes " +
            "FROM Transporte t " +
            "GROUP BY mes " +
            "ORDER BY DATE_FORMAT(t.fecha, '%Y-%m')")
    List<Object[]> countTransportesByDia();
}
