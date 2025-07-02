package com.unmsm.data_service.service;

import com.unmsm.data_service.model.MedicionInventario;
import com.unmsm.data_service.repository.MedicionInventarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicionInventarioService {

    private final MedicionInventarioRepository repo;

    public MedicionInventarioService(MedicionInventarioRepository repo) { this.repo = repo; }

    public List<MedicionInventario> listar() { return repo.findAll(); }

    // MedicionInventarioService.java
    public List<MedicionInventario> buscar(Long tanqueId, LocalDateTime ini,
                                           LocalDateTime fin, String comentario) {
        return repo.buscar(tanqueId, ini, fin, comentario);
    }

    public Optional<MedicionInventario> obtener(Long id) { return repo.findById(id); }

    public MedicionInventario guardar(MedicionInventario m) { return repo.save(m); }

    public void eliminar(Long id) { repo.deleteById(id); }
}
