package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recetamedica")
public class Recetamedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrecetamedica")
    private Integer idrecetamedica;

    @Column(name = "medicamento")
    private String medicamento;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idcita")
    private Cita cita;
}
