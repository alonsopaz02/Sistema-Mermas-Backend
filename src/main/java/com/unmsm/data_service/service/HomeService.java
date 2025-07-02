// service/HomeService.java
package com.unmsm.data_service.service;

import com.unmsm.data_service.dto.HomeSummary;
import com.unmsm.data_service.dto.MonthValue;
import com.unmsm.data_service.repository.HomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    private final HomeRepository repo;
    public HomeService(HomeRepository repo){ this.repo = repo; }

    public HomeSummary resumen(){ return repo.resumen(); }
    public List<MonthValue> tendencia(){ return repo.mermaMensual(); }
}
