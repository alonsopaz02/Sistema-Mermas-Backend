package com.unmsm.data_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "otras_operaciones")
@Data
public class OtrasOperaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tanque_id", referencedColumnName = "id")
    private Tanque tanque;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "volumen_observado")
    private Double volumenObservado;

    @Column(name = "volumen_60")
    private Double volumen60;
}
