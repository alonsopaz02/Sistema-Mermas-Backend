package com.unmsm.data_service.controller;

import com.unmsm.data_service.model.MedicionInventario;
import com.unmsm.data_service.service.MedicionInventarioService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mediciones")
@CrossOrigin(origins = "http://localhost:5173")   // ajusta si cambia tu front
public class MedicionInventarioController {

    private final MedicionInventarioService svc;

    public MedicionInventarioController(MedicionInventarioService svc) { this.svc = svc; }

    // MedicionInventarioController.java  (GET /api/mediciones)
    @GetMapping
    public List<MedicionInventario> listar(@RequestParam(required=false) Long tanqueId,
                                           @RequestParam(required=false) LocalDate inicio,
                                           @RequestParam(required=false) LocalDate fin,
                                           @RequestParam(required=false) String comentario) {

        if (tanqueId!=null || inicio!=null || fin!=null || comentario!=null) {
            LocalDateTime ini = inicio!=null ? inicio.atStartOfDay() : null;
            LocalDateTime fi  = fin   !=null ? fin.plusDays(1).atStartOfDay().minusSeconds(1) : null;
            return svc.buscar(tanqueId, ini, fi, comentario);
        }
        return svc.listar();
    }
    @PostMapping          public MedicionInventario crear(@RequestBody MedicionInventario m) { return svc.guardar(m); }
    @PutMapping("/{id}")  public MedicionInventario editar(@PathVariable Long id, @RequestBody MedicionInventario m) {
        m.setId(id); return svc.guardar(m);
    }
    @DeleteMapping("/{id}") public void eliminar(@PathVariable Long id) { svc.eliminar(id); }
}
