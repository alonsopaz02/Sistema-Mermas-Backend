package com.unmsm.data_service.controller;

import com.unmsm.data_service.dto.TransporteDTO;
import com.unmsm.data_service.model.Transporte;
import com.unmsm.data_service.model.Cisterna;
import com.unmsm.data_service.service.TransporteService;
import com.unmsm.data_service.service.CisternaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transportes")
public class TransporteController {

    @Autowired
    private TransporteService transporteService;

    @Autowired
    private CisternaService cisternaService;

    // Crear o actualizar un transporte
    @PostMapping
    public ResponseEntity<Transporte> createTransporte(@RequestBody TransporteDTO transporteDTO) {
        // Buscar cisterna usando el ID recibido
        Cisterna cisterna = cisternaService.getCisternaById(transporteDTO.getCisternaId())
                .orElseThrow(() -> new RuntimeException("Cisterna no encontrada"));

        // Crear el objeto de transporte
        Transporte transporte = new Transporte();
        transporte.setCisterna(cisterna);
        transporte.setFecha(transporteDTO.getFecha());
        transporte.setTemperaturaCarga(transporteDTO.getTemperaturaCarga());
        transporte.setTemperaturaDescarga(transporteDTO.getTemperaturaDescarga());
        transporte.setVolumenCargado(transporteDTO.getVolumenCargado());
        transporte.setVolumenCargado60(transporteDTO.getVolumenCargado60());
        transporte.setComentario(transporteDTO.getComentario());

        // Guardar transporte en la base de datos
        Transporte savedTransporte = transporteService.save(transporte);

        return ResponseEntity.ok(savedTransporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transporte> updateTransporte(@PathVariable Long id, @RequestBody TransporteDTO transporteDTO) {
        // Buscar el transporte con el ID proporcionado
        Transporte transporte = transporteService.getTransporteById(id)
                .orElseThrow(() -> new RuntimeException("Transporte no encontrado"));

        // Buscar cisterna usando el ID recibido
        Cisterna cisterna = cisternaService.getCisternaById(transporteDTO.getCisternaId())
                .orElseThrow(() -> new RuntimeException("Cisterna no encontrada"));

        // Actualizar el objeto de transporte con los nuevos datos
        transporte.setCisterna(cisterna);
        transporte.setFecha(transporteDTO.getFecha());
        transporte.setTemperaturaCarga(transporteDTO.getTemperaturaCarga());
        transporte.setTemperaturaDescarga(transporteDTO.getTemperaturaDescarga());
        transporte.setVolumenCargado(transporteDTO.getVolumenCargado());
        transporte.setVolumenCargado60(transporteDTO.getVolumenCargado60());
        transporte.setComentario(transporteDTO.getComentario());

        // Guardar el transporte actualizado en la base de datos
        Transporte updatedTransporte = transporteService.save(transporte);

        // Devolver el transporte actualizado en la respuesta
        return ResponseEntity.ok(updatedTransporte);
    }


    // Obtener todos los transportes
    @GetMapping
    public List<Transporte> getAllTransportes() {
        return transporteService.getAllTransportes();
    }

    // Obtener transporte por ID
    @GetMapping("/{id}")
    public ResponseEntity<Transporte> getTransporteById(@PathVariable Long id) {
        Optional<Transporte> transporte = transporteService.getTransporteById(id);
        return transporte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar transporte por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransporte(@PathVariable Long id) {
        transporteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener la cantidad de transportes por día
    @GetMapping("/meses")
    public List<Object[]> getTransportesPorDia() {
        return transporteService.getTransportesPorDia();
    }
}
