// dto/ReportFilter.java
package com.unmsm.data_service.dto;

public record ReportFilter(
        ReportType tipo,
        String fechaInicio,          // YYYY-MM-DD
        String fechaFin,             // YYYY-MM-DD
        Long estacionId,
        Long tanqueId,
        Long cisternaId,
        String responsable
) {}