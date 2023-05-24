package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name ="doctor")
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddoctor",  nullable = false)
    private Integer iddoctor;

    @ManyToOne
    @JoinColumn(name = "idespecialidad",  nullable = false)
    private Especialidad especialidad;

    @ManyToOne
    @JoinColumn(name = "idsede",  nullable = false)
    private Sede sede;

    @Column(name = "cmp",  nullable = false)
    private Integer cmp;

    @Column(name = "formacion",  nullable = false)
    private String formacion;

    @Column(name = "rne",  nullable = false)
    private Integer rne;

    @Column(name = "capacitaciones",  nullable = false)
    private String capacitaciones;

    @ManyToOne
    @JoinColumn(name = "idusuario",  nullable = false)
    private Usuario usuario;

    @Column(name = "zoom")
    private String zoom;

    @Column(name = "consultorio", nullable = false)
    private String consultorio;
}


