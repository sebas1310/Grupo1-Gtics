package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estadocita")
public class EstadoCita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestadocita",  nullable = false)
    private Integer idestadocita;

    @Column(name = "nombre")
    private String  nombre;
}
