// service/AlertService.java
package com.unmsm.data_service.service;

import com.unmsm.data_service.dto.AlertDTO;
import com.unmsm.data_service.dto.AlertView;
import com.unmsm.data_service.repository.AlertRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AlertRepository repo;

    public AlertService(AlertRepository repo) {
        this.repo = repo;
    }

    public Map<String, List<AlertDTO>> ultimas() {
        // Helper lambda para convertir la proyección en DTO
        java.util.function.Function<AlertView, AlertDTO> toDto =
                v -> new AlertDTO(
                        v.getId(),
                        v.getTipo(),
                        v.getTitulo(),
                        v.getDetalle(),
                        v.getFecha(),
                        Optional.ofNullable(v.getValor()).orElse(0d)
                );

        Map<String, List<AlertDTO>> map = new HashMap<>();

        map.put("MERMA_DIA",
                repo.mermaDia().stream().map(toDto).collect(Collectors.toList()));
        map.put("TEMP_EXTREMA",
                repo.temperatura().stream().map(toDto).collect(Collectors.toList()));
        map.put("MERMA_TRANS",
                repo.mermaTransporte().stream().map(toDto).collect(Collectors.toList()));

        return map;
    }
}

