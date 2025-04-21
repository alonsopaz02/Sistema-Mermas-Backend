package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transporte")
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cisterna_id", referencedColumnName = "id")
    private Cisterna cisterna;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "temperatura_carga")
    private Double temperaturaCarga;

    @Column(name = "temperatura_descarga")
    private Double temperaturaDescarga;

    @Column(name = "volumen_cargado")
    private Double volumenCargado;

    @Column(name = "volumen_cargado_60")
    private Double volumenCargado60;

    @Column(name = "comentario")
    private String comentario;
}
