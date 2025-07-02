// src/main/java/com/unmsm/data_service/dto/NumericView.java
package com.unmsm.data_service.dto;

/** Proyección genérica “etiqueta – valor numérico” usada por varios reportes */
public interface NumericView {
    String  getLabel();
    Double  getValue();
}