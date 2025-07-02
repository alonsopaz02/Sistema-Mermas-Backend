package com.unmsm.data_service.dto;
public interface MermaResumenView {

    String getFecha();                 // yyyy-MM-dd
    String getEstacion();              // nombre estación
    String getTanque();                // código tanque
    String getProducto();              // nombre producto

    /* Inventarios */
    Double getInventarioInicialObs();
    Double getInventarioInicial60();
    Double getInventarioFinalObs();
    Double getInventarioFinal60();

    /* Movimientos */
    Double getConsumoObs();
    Double getConsumo60();
    Double getOtrasOpsObs();
    Double getOtrasOps60();
    Double getDescargasObs();
    Double getDescargas60();
    Double getVentasObs();
    Double getVentas60();

    /* Métricas de merma */
    Double getMermaObs();
    Double getMerma60();
}