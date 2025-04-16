package com.unmsm.data_service.controller;

import com.unmsm.data_service.model.Cisterna;
import com.unmsm.data_service.service.CisternaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cisternas")
public class CisternaController {

    private final CisternaService cisternaService;

    @Autowired
    public CisternaController(CisternaService cisternaService) {
        this.cisternaService = cisternaService;
    }

    // Obtener todos los registros
    @GetMapping
    public List<Cisterna> getAllCisternas() {
        return cisternaService.getAllCisternas();
    }

    // Obtener un registro por ID
    @GetMapping("/{id}")
    public Optional<Cisterna> getCisternaById(@PathVariable Long id) {
        return cisternaService.getCisternaById(id);
    }

    // Crear o actualizar una cisterna
    @PostMapping
    public Cisterna createCisterna(@RequestBody Cisterna cisterna) {
        return cisternaService.saveCisterna(cisterna);
    }

    // Actualizar una cisterna
    @PutMapping("/{id}")
    public Cisterna updateCisterna(@PathVariable Long id, @RequestBody Cisterna cisterna) {
        cisterna.setId(id);
        return cisternaService.saveCisterna(cisterna);
    }

    // Eliminar una cisterna
    @DeleteMapping("/{id}")
    public void deleteCisterna(@PathVariable Long id) {
        cisternaService.deleteCisterna(id);
    }
}
