package com.unmsm.data_service.service;

import com.unmsm.data_service.model.Transporte;
import com.unmsm.data_service.repository.TransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransporteService {

    @Autowired
    private TransporteRepository transporteRepository;

    // Guardar o actualizar una operación
    public Transporte save(Transporte transporte) {
        return transporteRepository.save(transporte);
    }

    // Obtener todas las operaciones de transporte
    public List<Transporte> getAllTransportes() {
        return transporteRepository.findAll();
    }

    // Obtener transporte por ID
    public Optional<Transporte> getTransporteById(Long id) {
        return transporteRepository.findById(id);
    }

    // Eliminar una operación de transporte
    public void delete(Long id) {
        transporteRepository.deleteById(id);
    }

    // Método para obtener la cantidad de transportes por día
    public List<Object[]> getTransportesPorDia() {
        return transporteRepository.countTransportesByDia();
    }
}
