package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "consumo_tanque")
@Data  // Lombok: genera getters, setters, equals, hashCode, toString, y constructor
public class ConsumoTanque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tanque_id")
    private Tanque tanque;  // Relación con la entidad Tanque

    @Column(name = "volumen_consumido")
    private Double volumenConsumido;

    @Column(name = "volumen_60")
    private Double volumen60;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "motivo_consumo")
    private String motivoConsumo;

    // Constructor, getters, setters, etc. son generados por Lombok
}
