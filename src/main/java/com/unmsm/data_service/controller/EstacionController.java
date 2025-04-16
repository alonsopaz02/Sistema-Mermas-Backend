package com.unmsm.data_service.controller;

import com.unmsm.data_service.model.Estacion;
import com.unmsm.data_service.service.EstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    @Autowired
    private EstacionService estacionService;

    // Obtener todas las estaciones
    @GetMapping
    public List<Estacion> getAllEstaciones() {
        return estacionService.getAllEstaciones();
    }

    // Obtener estación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estacion> getEstacionById(@PathVariable Long id) {
        Optional<Estacion> estacion = estacionService.getEstacionById(id);
        if (estacion.isPresent()) {
            return ResponseEntity.ok(estacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Agregar una nueva estación
    @PostMapping
    public Estacion createEstacion(@RequestBody Estacion estacion) {
        return estacionService.saveEstacion(estacion);
    }

    // Actualizar una estación existente
    @PutMapping("/{id}")
    public ResponseEntity<Estacion> updateEstacion(@PathVariable Long id, @RequestBody Estacion estacion) {
        Optional<Estacion> existingEstacion = estacionService.getEstacionById(id);
        if (existingEstacion.isPresent()) {
            estacion.setId(id);
            Estacion updatedEstacion = estacionService.saveEstacion(estacion);
            return ResponseEntity.ok(updatedEstacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una estación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstacion(@PathVariable Long id) {
        Optional<Estacion> estacion = estacionService.getEstacionById(id);
        if (estacion.isPresent()) {
            estacionService.deleteEstacion(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
