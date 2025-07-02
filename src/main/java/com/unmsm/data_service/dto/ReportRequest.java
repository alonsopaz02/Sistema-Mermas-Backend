// dto/ReportRequest.java
package com.unmsm.data_service.dto;

public record ReportRequest(
        String tipo,              // transporte|inventario|operaciones|temperatura|responsable
        String fechaInicio,
        String fechaFin,
        Long estacionId,
        Long tanqueId,
        Long cisternaId,
        Long productoId
) {}
