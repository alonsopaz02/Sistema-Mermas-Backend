package com.unmsm.data_service.controller;

import com.unmsm.data_service.model.Tanque;
import com.unmsm.data_service.service.TanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tanques")
public class TanqueController {

    @Autowired
    private TanqueService tanqueService;

    // Obtener todos los tanques
    @GetMapping
    public List<Tanque> getAllTanques() {
        return tanqueService.getAllTanques();
    }

    // Obtener un tanque por ID
    @GetMapping("/{id}")
    public Optional<Tanque> getTanqueById(@PathVariable Long id) {
        return tanqueService.getTanqueById(id);
    }

    // Crear un nuevo tanque
    @PostMapping
    public Tanque createTanque(@RequestBody Tanque tanque) {
        return tanqueService.createTanque(tanque);
    }

    // Actualizar un tanque existente
    @PutMapping("/{id}")
    public Tanque updateTanque(@PathVariable Long id, @RequestBody Tanque tanque) {
        return tanqueService.updateTanque(id, tanque);
    }

    // Eliminar un tanque
    @DeleteMapping("/{id}")
    public void deleteTanque(@PathVariable Long id) {
        tanqueService.deleteTanque(id);
    }
}
