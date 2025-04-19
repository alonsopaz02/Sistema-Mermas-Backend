package com.unmsm.data_service.controller;

import com.unmsm.data_service.model.ConsumoTanque;
import com.unmsm.data_service.service.ConsumoTanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consumos")
public class ConsumoTanqueController {

    @Autowired
    private ConsumoTanqueService consumoTanqueService;

    // Obtener todos los consumos de tanques
    @GetMapping
    public List<ConsumoTanque> getAllConsumos() {
        return consumoTanqueService.getAllConsumos();
    }

    // Obtener consumo de tanque por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConsumoTanque> getConsumoById(@PathVariable Long id) {
        Optional<ConsumoTanque> consumoTanque = consumoTanqueService.getConsumoById(id);
        return consumoTanque.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear o actualizar un consumo de tanque
    @PostMapping
    public ResponseEntity<ConsumoTanque> createConsumo(@RequestBody ConsumoTanque consumoTanque) {
        ConsumoTanque savedConsumo = consumoTanqueService.saveConsumo(consumoTanque);
        return ResponseEntity.ok(savedConsumo);
    }

    // Eliminar un consumo de tanque por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumo(@PathVariable Long id) {
        consumoTanqueService.deleteConsumo(id);
        return ResponseEntity.noContent().build();
    }
}
