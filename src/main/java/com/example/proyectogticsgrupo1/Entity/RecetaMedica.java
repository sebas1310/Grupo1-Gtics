package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recetamedica")
public class RecetaMedica {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrecetamedica", nullable = false)
    private Integer idRecetaMedica;

    @Column(name = "medicamento", length = 100, nullable = false)
    private String medicamento;

    @Column(name = "dosis", length = 100, nullable = false)
    private String dosis;

    @Column(name = "descripcion",nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idcita", nullable = false)
    private Cita cita;


}