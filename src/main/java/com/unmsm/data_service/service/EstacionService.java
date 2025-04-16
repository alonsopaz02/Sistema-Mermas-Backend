package com.unmsm.data_service.service;

import com.unmsm.data_service.model.Estacion;
import com.unmsm.data_service.repository.EstacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    // Obtener todas las estaciones
    public List<Estacion> getAllEstaciones() {
        return estacionRepository.findAll();
    }

    // Obtener una estación por ID
    public Optional<Estacion> getEstacionById(Long id) {
        return estacionRepository.findById(id);
    }

    // Guardar una nueva estación o actualizar una existente
    public Estacion saveEstacion(Estacion estacion) {
        return estacionRepository.save(estacion);
    }

    // Eliminar una estación por ID
    public void deleteEstacion(Long id) {
        estacionRepository.deleteById(id);
    }
}
