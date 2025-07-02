// dto/AlertDTO.java
package com.unmsm.data_service.dto;

public record AlertDTO(
        Long   id,             // autogen → permite “cerrar” alertas en el futuro
        String tipo,           // MERMA_DIA | TEMP_EXTREMA | MERMA_TRANS
        String titulo,         // texto corto para la tarjeta
        String detalle,        // mensaje completo
        java.time.LocalDate fecha,   // cuándo se detectó
        double valor           // valor que disparó la alerta
) {}
