package com.unmsm.data_service.controller;

import com.unmsm.data_service.repository.PersonalRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@CrossOrigin(origins = "http://localhost:5173")
public class CatalogoController {

    private final PersonalRepository personalRepo;
    public CatalogoController(PersonalRepository p){ this.personalRepo = p; }

    @GetMapping("/responsables")
    public List<String> listarResponsables() {
        return personalRepo.responsables();
    }
}
