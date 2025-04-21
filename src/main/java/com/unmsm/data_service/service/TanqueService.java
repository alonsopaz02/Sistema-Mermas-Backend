package com.unmsm.data_service.service;

import com.unmsm.data_service.model.Producto;
import com.unmsm.data_service.model.Tanque;
import com.unmsm.data_service.repository.TanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TanqueService {

    @Autowired
    private TanqueRepository tanqueRepository;

    // Obtener todos los tanques
    public List<Tanque> getAllTanques() {
        return tanqueRepository.findAll();
    }

    // Obtener tanque por ID
    public Optional<Tanque> getTanqueById(Long id) {
        return tanqueRepository.findById(id);
    }

    // Crear nuevo tanque
    public Tanque createTanque(Tanque tanque) {
        return tanqueRepository.save(tanque);
    }

    // Actualizar tanque existente
    public Tanque updateTanque(Long id, Tanque tanqueDetails) {
        Optional<Tanque> optionalTanque = tanqueRepository.findById(id);
        if (optionalTanque.isPresent()) {
            Tanque tanque = optionalTanque.get();
            tanque.setCodigo(tanqueDetails.getCodigo());
            tanque.setEstacion(tanqueDetails.getEstacion());
            tanque.setProducto(tanqueDetails.getProducto());
            tanque.setCapacidadLitros(tanqueDetails.getCapacidadLitros());
            tanque.setVolumenActual(tanqueDetails.getVolumenActual());
            tanque.setVolumen60(tanqueDetails.getVolumen60());
            tanque.setTemperaturaMedia(tanqueDetails.getTemperaturaMedia());
            tanque.setPorcentajeOcupacion(tanqueDetails.getPorcentajeOcupacion());
            tanque.setEstado(tanqueDetails.getEstado());
            tanque.setUltimaActualizacion(tanqueDetails.getUltimaActualizacion());
            return tanqueRepository.save(tanque);
        }
        return null;
    }


    // Eliminar tanque
    public void deleteTanque(Long id) {
        tanqueRepository.deleteById(id);
    }
}
