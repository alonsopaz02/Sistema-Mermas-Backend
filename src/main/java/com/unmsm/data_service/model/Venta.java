package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data

@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tanque_id", referencedColumnName = "id")
    private Tanque tanque;

    @Column(name = "volumen_vendido")
    private Double volumenVendido;

    @Column(name = "volumen_60")
    private Double volumen60;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "descripcion")
    private String descripcion;

}
