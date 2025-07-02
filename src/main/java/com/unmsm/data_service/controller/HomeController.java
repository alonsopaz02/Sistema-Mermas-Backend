// controller/HomeController.java
package com.unmsm.data_service.controller;

import com.unmsm.data_service.dto.HomeSummary;
import com.unmsm.data_service.dto.MonthValue;
import com.unmsm.data_service.service.HomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "http://localhost:5173")
public class HomeController {

    private final HomeService service;
    public HomeController(HomeService s){ this.service = s; }

    @GetMapping("/summary")
    public HomeSummary resumen(){ return service.resumen(); }

    @GetMapping("/tendencia")
    public List<MonthValue> tendencia(){ return service.tendencia(); }
}
