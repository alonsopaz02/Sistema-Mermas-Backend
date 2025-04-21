package com.unmsm.data_service.service;

import com.unmsm.data_service.model.OtrasOperaciones;
import com.unmsm.data_service.repository.OtrasOperacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OtrasOperacionesService {

    @Autowired
    private OtrasOperacionesRepository otrasOperacionesRepository;

    // Guardar o actualizar una operación
    public OtrasOperaciones save(OtrasOperaciones otrasOperaciones) {
        return otrasOperacionesRepository.save(otrasOperaciones);
    }

    // Obtener todas las operaciones
    public List<OtrasOperaciones> getAllOtrasOperaciones() {
        return otrasOperacionesRepository.findAll();
    }

    // Obtener una operación por su ID
    public Optional<OtrasOperaciones> getOtrasOperacionesById(Long id) {
        return otrasOperacionesRepository.findById(id);
    }

    // Eliminar una operación por su ID
    public void delete(Long id) {
        otrasOperacionesRepository.deleteById(id);
    }
}
