package com.unmsm.data_service.controller;

import com.unmsm.data_service.dto.ReportFilter;
import com.unmsm.data_service.dto.ReportType;
import com.unmsm.data_service.service.ReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Expone /api/reportes
 * Devuelve un PDF con el contenido indicado por los filtros.
 *
 * Ejemplo:
 *   GET /api/reportes?tipo=MERMA_TRANSPORTE&
 *       fechaInicio=2023-01-01&fechaFin=2023-01-31&cisternaId=2
 */
@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:5173")   // ajusta tu host del frontend
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generar(
            /* --------- parámetros obligatorios --------- */
            @RequestParam ReportType tipo,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            /* --------- parámetros opcionales (pueden ser null) --------- */
            @RequestParam(required = false) Long estacionId,
            @RequestParam(required = false) Long tanqueId,
            @RequestParam(required = false) Long cisternaId,
            @RequestParam(required = false) String responsable
    ) {
        /* construimos el filtro con todos los datos */
        ReportFilter filtro = new ReportFilter(
                tipo, fechaInicio, fechaFin,
                estacionId, tanqueId, cisternaId, responsable
        );

        byte[] pdf = service.generarPdf(filtro);

        String nombre = "reporte-%s.pdf".formatted(tipo.name().toLowerCase());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + nombre)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
