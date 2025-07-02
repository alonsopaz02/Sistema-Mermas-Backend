package com.unmsm.data_service.service;

import com.unmsm.data_service.model.MedicionTemperatura;
import com.unmsm.data_service.repository.MedicionTemperaturaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicionTemperaturaService {

    private final MedicionTemperaturaRepository repo;

    public MedicionTemperaturaService(MedicionTemperaturaRepository repo) {
        this.repo = repo;
    }

    public List<MedicionTemperatura> listar() { return repo.findAll(); }

    public List<MedicionTemperatura> buscar(Long tanqueId, LocalDateTime ini,
                                            LocalDateTime fin, String tipo) {
        return repo.buscar(tanqueId, ini, fin, tipo);
    }

    public Optional<MedicionTemperatura> obtener(Long id) { return repo.findById(id); }

    public MedicionTemperatura guardar(MedicionTemperatura m) { return repo.save(m); }

    public void eliminar(Long id) { repo.deleteById(id); }
}
