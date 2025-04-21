package com.unmsm.data_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OtrasOperacionesDTO {

    private Long tanqueId;
    private LocalDateTime fecha;
    private String descripcion;
    private Double volumenObservado;
    private Double volumen60;
}
