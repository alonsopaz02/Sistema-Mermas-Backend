// dto/AlertView.java  (proyección de Spring Data)
package com.unmsm.data_service.dto;

import java.time.LocalDate;

/**
 *  *La fila* que JPA leerá de la consulta nativa.
 *  Los nombres de los getter **deben** coincidir con los alias.
 */
public interface AlertView {
    Long       getId();
    String     getTipo();
    String     getTitulo();
    String     getDetalle();
    LocalDate  getFecha();
    Double     getValor();
}