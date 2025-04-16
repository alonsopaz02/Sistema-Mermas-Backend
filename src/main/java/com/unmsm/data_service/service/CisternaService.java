package com.unmsm.data_service.service;

import com.unmsm.data_service.model.Cisterna;
import com.unmsm.data_service.repository.CisternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CisternaService {

    private final CisternaRepository cisternaRepository;

    @Autowired
    public CisternaService(CisternaRepository cisternaRepository) {
        this.cisternaRepository = cisternaRepository;
    }

    // Obtener todos los registros
    public List<Cisterna> getAllCisternas() {
        return cisternaRepository.findAll();
    }

    // Obtener un registro por ID
    public Optional<Cisterna> getCisternaById(Long id) {
        return cisternaRepository.findById(id);
    }

    // Crear o actualizar una cisterna
    public Cisterna saveCisterna(Cisterna cisterna) {
        return cisternaRepository.save(cisterna);
    }

    // Eliminar una cisterna
    public void deleteCisterna(Long id) {
        cisternaRepository.deleteById(id);
    }
}
