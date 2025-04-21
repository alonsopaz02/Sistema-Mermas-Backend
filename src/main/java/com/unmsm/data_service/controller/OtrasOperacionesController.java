package com.unmsm.data_service.controller;

import com.unmsm.data_service.dto.OtrasOperacionesDTO;
import com.unmsm.data_service.model.OtrasOperaciones;
import com.unmsm.data_service.model.Tanque;
import com.unmsm.data_service.service.OtrasOperacionesService;
import com.unmsm.data_service.service.TanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/otras-operaciones")
public class OtrasOperacionesController {

    @Autowired
    private OtrasOperacionesService otrasOperacionesService;

    @Autowired
    private TanqueService tanqueService;

    // POST: Crear nueva operación
    @PostMapping
    public ResponseEntity<OtrasOperaciones> createOtrasOperaciones(@RequestBody OtrasOperacionesDTO otrasOperacionesDTO) {
        // Buscar el tanque usando el ID recibido
        Tanque tanque = tanqueService.getTanqueById(otrasOperacionesDTO.getTanqueId())
                .orElseThrow(() -> new RuntimeException("Tanque no encontrado"));

        // Crear la nueva operación
        OtrasOperaciones otrasOperaciones = new OtrasOperaciones();
        otrasOperaciones.setTanque(tanque);
        otrasOperaciones.setFecha(otrasOperacionesDTO.getFecha());
        otrasOperaciones.setDescripcion(otrasOperacionesDTO.getDescripcion());
        otrasOperaciones.setVolumenObservado(otrasOperacionesDTO.getVolumenObservado());
        otrasOperaciones.setVolumen60(otrasOperacionesDTO.getVolumen60());

        // Guardar la operación en la base de datos
        OtrasOperaciones savedOtrasOperaciones = otrasOperacionesService.save(otrasOperaciones);

        return ResponseEntity.ok(savedOtrasOperaciones);
    }

    // GET: Obtener todas las operaciones
    @GetMapping
    public ResponseEntity<List<OtrasOperaciones>> getAllOtrasOperaciones() {
        List<OtrasOperaciones> otrasOperaciones = otrasOperacionesService.getAllOtrasOperaciones();
        return ResponseEntity.ok(otrasOperaciones);
    }

    // GET: Obtener una operación específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<OtrasOperaciones> getOtrasOperacionesById(@PathVariable Long id) {
        Optional<OtrasOperaciones> otrasOperaciones = otrasOperacionesService.getOtrasOperacionesById(id);
        return otrasOperaciones.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: Actualizar una operación existente
    @PutMapping("/{id}")
    public ResponseEntity<OtrasOperaciones> updateOtrasOperaciones(
            @PathVariable Long id, @RequestBody OtrasOperacionesDTO otrasOperacionesDTO) {
        // Verificar si la operación existe
        Optional<OtrasOperaciones> existingOperaciones = otrasOperacionesService.getOtrasOperacionesById(id);
        if (!existingOperaciones.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Buscar el tanque usando el ID recibido
        Tanque tanque = tanqueService.getTanqueById(otrasOperacionesDTO.getTanqueId())
                .orElseThrow(() -> new RuntimeException("Tanque no encontrado"));

        // Actualizar la operación
        OtrasOperaciones updatedOperaciones = existingOperaciones.get();
        updatedOperaciones.setTanque(tanque);
        updatedOperaciones.setFecha(otrasOperacionesDTO.getFecha());
        updatedOperaciones.setDescripcion(otrasOperacionesDTO.getDescripcion());
        updatedOperaciones.setVolumenObservado(otrasOperacionesDTO.getVolumenObservado());
        updatedOperaciones.setVolumen60(otrasOperacionesDTO.getVolumen60());

        // Guardar la operación actualizada
        OtrasOperaciones savedOtrasOperaciones = otrasOperacionesService.save(updatedOperaciones);

        return ResponseEntity.ok(savedOtrasOperaciones);
    }

    // DELETE: Eliminar una operación específica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOtrasOperaciones(@PathVariable Long id) {
        Optional<OtrasOperaciones> existingOperaciones = otrasOperacionesService.getOtrasOperacionesById(id);
        if (existingOperaciones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Eliminar la operación
        otrasOperacionesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
