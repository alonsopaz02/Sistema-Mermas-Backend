package com.unmsm.data_service.controller;

import com.unmsm.data_service.model.Venta;
import com.unmsm.data_service.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Obtener todas las ventas
    @GetMapping
    public List<Map<String, Object>> getAllVentas() {
        List<Map<String, Object>> ventasData = new ArrayList<>();

        // Obtener todas las ventas
        List<Venta> ventas = ventaService.getAllVentas();

        // Iterar sobre cada venta
        for (Venta venta : ventas) {
            Map<String, Object> ventaMap = new HashMap<>();
            // Rellenamos el mapa con los datos de la venta
            ventaMap.put("id", venta.getId());
            ventaMap.put("volumenVendido", venta.getVolumenVendido());
            ventaMap.put("volumen60", venta.getVolumen60());
            ventaMap.put("cliente", venta.getCliente());
            ventaMap.put("descripcion", venta.getDescripcion());
            ventaMap.put("fecha", venta.getFecha());

            // Solo devolvemos el ID y código del tanque
            Map<String, Object> tanque = new HashMap<>();
            tanque.put("id", venta.getTanque().getId());
            tanque.put("codigo", venta.getTanque().getCodigo());
            ventaMap.put("tanque", tanque);

            ventasData.add(ventaMap);
        }

        return ventasData;
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        Optional<Venta> venta = ventaService.getVentaById(id);
        return venta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva venta o actualizar una existente
    @PostMapping
    public Venta createVenta(@RequestBody Venta venta) {
        return ventaService.saveOrUpdateVenta(venta);
    }

    // Actualizar una venta existente
    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        Optional<Venta> existingVenta = ventaService.getVentaById(id);
        if (existingVenta.isPresent()) {
            venta.setId(id);
            Venta updatedVenta = ventaService.saveOrUpdateVenta(venta);
            return ResponseEntity.ok(updatedVenta);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.deleteVenta(id);
        return ResponseEntity.noContent().build();
    }
}
