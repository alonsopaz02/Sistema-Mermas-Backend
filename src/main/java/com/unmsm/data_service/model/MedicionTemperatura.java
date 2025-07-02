package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medicion_temperatura")
public class MedicionTemperatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")                      // 🔹 mapeo explícito
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tanque_id")           // 🔹 FK explícita
    private Tanque tanque;

    @Column(name = "temperatura")
    private Double temperatura;

    @Column(name = "fecha")
    private java.time.LocalDateTime fecha;

    @Column(name = "tipo_medicion")
    private String tipoMedicion;

    @Column(name = "comentario")
    private String comentario;
}
