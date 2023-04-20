package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//@Entity
@Getter
@Setter
//@Table(name="doctor")
public class Doctor {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="iddoctor",nullable = false)
    private Integer idDoctor;

    @ManyToOne
    @JoinColumn(name = "idespecialidad")
    private Especialidad especialidad;

    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sede;
    @Column(name="cmp",nullable = false)
    private Integer cmp;

    @Column(name="formacion",nullable = false, length = 500)
    private String formacion;
    @Column(name="rne",nullable = false)
    private Integer rne;

    @Column(name="capacitaciones",nullable = false, length = 500)
    private String capacitaciones;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

}