package com.unmsm.data_service.model;

import jakarta.persistence.GenerationType;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;


@Entity
@Data
public class Estacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ubicacion;
    private String tipo;
    private String descripcion;
}
