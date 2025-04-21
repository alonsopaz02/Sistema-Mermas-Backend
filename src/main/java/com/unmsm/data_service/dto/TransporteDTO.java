package com.unmsm.data_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransporteDTO {

    private Long cisternaId;
    private LocalDateTime fecha;
    private Double temperaturaCarga;
    private Double temperaturaDescarga;
    private Double volumenCargado;
    private Double volumenCargado60;
    private String comentario;


}
