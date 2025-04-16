package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tanque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @ManyToOne
    @JoinColumn(name = "estacion_id")
    private Estacion estacion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Double capacidadLitros;
    private Double volumenActual;
    private Double volumen60;
    private Double temperaturaMedia;
    private Double porcentajeOcupacion;
    private String estado;
    private String ultimaActualizacion;
}
