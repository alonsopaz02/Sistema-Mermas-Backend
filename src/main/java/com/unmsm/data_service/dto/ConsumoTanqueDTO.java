package com.unmsm.data_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data  // Lombok: genera getters, setters, equals, hashCode, toString, y constructor
public class ConsumoTanqueDTO {

    private Long tanqueId;
    private Double volumenConsumido;
    private Double volumen60;
    private LocalDateTime fecha;  // Cambiado a LocalDateTime
    private String motivoConsumo;

    // Getters and Setters
}

