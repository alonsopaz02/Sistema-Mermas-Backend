package com.unmsm.data_service.controller;

import com.unmsm.data_service.dto.MermaResumenView;
import com.unmsm.data_service.service.MermaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/mermas")
@CrossOrigin(origins = "http://localhost:5173")   // Ajusta si tu front corre en otro host/puerto
public class MermaController {

    private final MermaService mermaService;

    public MermaController(MermaService mermaService) {
        this.mermaService = mermaService;
    }

    /** GET /api/mermas?inicio=2023-01-01&fin=2023-01-31&estacionId=1&tanqueId=5 */
    @GetMapping
    public List<MermaResumenView> listarMermas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin,
            @RequestParam(required = false) Long estacionId,
            @RequestParam(required = false) Long tanqueId) {

        return mermaService.obtenerMermas(inicio, fin, estacionId, tanqueId);
    }
}
