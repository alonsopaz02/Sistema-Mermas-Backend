package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumo_tanque")
@Data  // Lombok: genera getters, setters, equals, hashCode, toString, y constructor
public class ConsumoTanque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tanque_id", referencedColumnName = "id")
    private Tanque tanque;

    @Column(name = "volumen_consumido")
    private Double volumenConsumido;

    @Column(name = "volumen_60")
    private Double volumen60;

    @Column(name = "fecha")
    private LocalDateTime fecha;  // Cambiado a LocalDateTime

    @Column(name = "motivo_consumo")
    private String motivoConsumo;

    // Getters and Setters
}
