package com.unmsm.data_service.controller;

import com.unmsm.data_service.dto.ConsumoTanqueDTO;
import com.unmsm.data_service.model.ConsumoTanque;
import com.unmsm.data_service.model.Tanque;
import com.unmsm.data_service.service.ConsumoTanqueService;
import com.unmsm.data_service.service.TanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consumos")
public class ConsumoTanqueController {

    @Autowired
    private TanqueService tanqueService;

    @Autowired
    private ConsumoTanqueService consumoTanqueService;

    // Crear un consumo de tanque
    @PostMapping
    public ResponseEntity<ConsumoTanque> createConsumo(@RequestBody ConsumoTanqueDTO consumoTanqueDTO) {
        // Obtener tanque por ID
        Tanque tanque = tanqueService.getTanqueById(consumoTanqueDTO.getTanqueId())
                .orElseThrow(() -> new RuntimeException("Tanque no encontrado"));

        // Crear el objeto ConsumoTanque y asignar el tanque encontrado
        ConsumoTanque consumo = new ConsumoTanque();
        consumo.setTanque(tanque);
        consumo.setVolumenConsumido(consumoTanqueDTO.getVolumenConsumido());
        consumo.setVolumen60(consumoTanqueDTO.getVolumen60());
        consumo.setFecha(consumoTanqueDTO.getFecha()); // Usar LocalDateTime directamente
        consumo.setMotivoConsumo(consumoTanqueDTO.getMotivoConsumo());

        // Guardar el consumo
        ConsumoTanque savedConsumo = consumoTanqueService.save(consumo);

        return ResponseEntity.ok(savedConsumo);
    }

    // GET: Obtener todas las operaciones
    @GetMapping
    public ResponseEntity<List<ConsumoTanque>> getAllConsumos() {
        List<ConsumoTanque> consumos = consumoTanqueService.getAllConsumos();
        return ResponseEntity.ok(consumos);
    }

    // Obtener consumo de tanque por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConsumoTanque> getConsumoById(@PathVariable Long id) {
        Optional<ConsumoTanque> consumoTanque = consumoTanqueService.getConsumoById(id);
        return consumoTanque.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumo(@PathVariable Long id) {
        // Buscar el ConsumoTanque por el ID
        Optional<ConsumoTanque> existingConsumo = consumoTanqueService.getConsumoById(id);
        if (existingConsumo.isEmpty()) {
            // Si no se encuentra el ConsumoTanque, devolver un 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // Eliminar el ConsumoTanque
        consumoTanqueService.delete(id);
        return ResponseEntity.noContent().build(); // Respuesta 204 No Content
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumoTanque> updateConsumo(@PathVariable Long id, @RequestBody ConsumoTanqueDTO consumoTanqueDTO) {
        // Buscar el ConsumoTanque por ID
        Optional<ConsumoTanque> existingConsumo = consumoTanqueService.getConsumoById(id);
        if (existingConsumo.isEmpty()) {
            // Si no se encuentra el ConsumoTanque, devolver un 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // Obtener el tanque por ID
        Tanque tanque = tanqueService.getTanqueById(consumoTanqueDTO.getTanqueId())
                .orElseThrow(() -> new RuntimeException("Tanque no encontrado"));

        // Crear el objeto ConsumoTanque y actualizar los valores
        ConsumoTanque consumo = existingConsumo.get();
        consumo.setTanque(tanque); // Asignar el tanque actualizado
        consumo.setVolumenConsumido(consumoTanqueDTO.getVolumenConsumido());
        consumo.setVolumen60(consumoTanqueDTO.getVolumen60());
        consumo.setFecha(consumoTanqueDTO.getFecha()); // Actualizar la fecha
        consumo.setMotivoConsumo(consumoTanqueDTO.getMotivoConsumo());

        // Guardar el ConsumoTanque actualizado
        ConsumoTanque updatedConsumo = consumoTanqueService.save(consumo);

        return ResponseEntity.ok(updatedConsumo); // Retornar el ConsumoTanque actualizado
    }
}
