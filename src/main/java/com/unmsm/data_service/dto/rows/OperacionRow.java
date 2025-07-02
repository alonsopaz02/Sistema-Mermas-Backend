package com.unmsm.data_service.dto.rows;

public interface OperacionRow {
    String  getFecha();
    String  getTipo();          // DESCARGA | VENTA | CONSUMO | OTRA
    String  getEntidad();       // Cisterna o Cliente
    Double  getVolumen60();
}