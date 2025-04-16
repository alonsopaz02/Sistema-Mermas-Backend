package com.unmsm.data_service.model;

import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;



@Entity
@Data  // Lombok generará automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Lombok generará un constructor sin argumentos
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;

    // El resto del código es innecesario con Lombok gracias a la anotación @Data
}
