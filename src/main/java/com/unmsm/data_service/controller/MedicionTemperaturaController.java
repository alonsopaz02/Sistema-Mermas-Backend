package com.unmsm.data_service.controller;

import com.unmsm.data_service.model.MedicionTemperatura;
import com.unmsm.data_service.service.MedicionTemperaturaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/temperaturas")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicionTemperaturaController {

    private final MedicionTemperaturaService svc;

    public MedicionTemperaturaController(MedicionTemperaturaService svc) { this.svc = svc; }

    /** GET con filtros opcionales (?tanqueId&inicio&fin&tipoMedicion) */
    @GetMapping
    public List<MedicionTemperatura> listar(@RequestParam(required = false) Long tanqueId,
                                            @RequestParam(required = false) LocalDate inicio,
                                            @RequestParam(required = false) LocalDate fin,
                                            @RequestParam(required = false) String tipoMedicion) {

        if (tanqueId != null || inicio != null || fin != null || tipoMedicion != null) {
            LocalDateTime ini = inicio != null ? inicio.atStartOfDay() : null;
            LocalDateTime fi  = fin    != null ? fin.plusDays(1).atStartOfDay().minusSeconds(1) : null;
            return svc.buscar(tanqueId, ini, fi, tipoMedicion);
        }
        return svc.listar();
    }

    @PostMapping           public MedicionTemperatura crear(@RequestBody MedicionTemperatura m){return svc.guardar(m);}
    @PutMapping("/{id}")   public MedicionTemperatura editar(@PathVariable Long id,@RequestBody MedicionTemperatura m){m.setId(id);return svc.guardar(m);}
    @DeleteMapping("/{id}") public void eliminar(@PathVariable Long id){svc.eliminar(id);}
}
