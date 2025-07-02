// dto/HomeSummary.java
package com.unmsm.data_service.dto;

public interface HomeSummary {
    /* valores numéricos ya convertidos a litros/unidades */
    double getMermaTotal();            /* L 60°  (transporte + tanques)   */
    double getVolumenTransportado();   /* L 60°  */
    int    getEventosTransporte();     /* Nº transportes                 */
    int    getVehiculosActivos();      /* Cisternas                      */
    int    getTanquesActivos();        /* Tanques                        */
    int    getEstacionesActivas();     /* Estaciones                     */
    int    getConductoresActivos();    /* Responsables únicos            */
}