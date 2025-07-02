package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medicion_inventario")
public class MedicionInventario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "tanque_id")
    private Tanque tanque;

    private java.time.LocalDateTime fecha;

    @Column(name = "inventario_obs")
    private Double inventarioObs;

    @Column(name = "inventario_60")   // siempre mapea explícitamente
    private Double inventario60;
    private String responsable;
    private String comentario;
}
