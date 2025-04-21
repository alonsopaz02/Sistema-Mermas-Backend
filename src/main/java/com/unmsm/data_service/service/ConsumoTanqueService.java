package com.unmsm.data_service.service;

import com.unmsm.data_service.model.ConsumoTanque;
import com.unmsm.data_service.repository.ConsumoTanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumoTanqueService {

    @Autowired
    private ConsumoTanqueRepository consumoTanqueRepository;

    // Obtener todos los consumos de los tanques
    public List<ConsumoTanque> getAllConsumos() {
        return consumoTanqueRepository.findAll();
    }

    // Obtener consumo por ID
    public Optional<ConsumoTanque> getConsumoById(Long id) {
        return consumoTanqueRepository.findById(id);
    }

    // Crear o actualizar un consumo de tanque
    public ConsumoTanque save(ConsumoTanque consumoTanque) {
        return consumoTanqueRepository.save(consumoTanque);
    }

    // Eliminar un consumo de tanque
    public void delete(Long id) {
        consumoTanqueRepository.deleteById(id);
    }
}
