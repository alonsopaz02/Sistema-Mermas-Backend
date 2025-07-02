// src/main/java/com/unmsm/data_service/controller/DashboardController.java
package com.unmsm.data_service.controller;

import com.unmsm.data_service.dto.DashboardDTO;
import com.unmsm.data_service.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")   // ajuste si tu front corre en otro host/puerto
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping
    public DashboardDTO obtener() {
        return service.generar();
    }
}
