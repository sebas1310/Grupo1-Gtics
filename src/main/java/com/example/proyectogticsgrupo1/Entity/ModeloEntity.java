package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "modelo")
public class ModeloEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmodelo")
    private int idmodelo;
    @Basic
    @Column(name = "nombre",nullable = false)
    private String nombre;
    @Basic
    @Column(name = "preguntas",nullable = false)
    private String preguntas;
    @Basic
    @Column(name = "formulario")
    private Byte formulario;
    @Basic
    @Column(name = "informe")
    private Byte informe;
    @ManyToOne
    @JoinColumn(name = "idtipodeusuario",nullable = false)
    private Tipodeusuario tipodeusuario;
    @ManyToOne
    @JoinColumn(name = "idespecialidad")
    private Especialidad especialidad;
    @ManyToOne
    @JoinColumn(name = "idcita")
    private Cita cita;
    @Basic
    @Column(name = "nro_inputs")
    private Integer nroInputs;
    @Basic
    @Column(name = "cuestionario")
    private Byte cuestionario;

}
