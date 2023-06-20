package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Getter
@Setter
@Table(name = "uxui")
public class UxUiEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUX/UI")
    private int idUxUi;
    @Basic
    @Column(name = "modo",nullable = false)
    private String modo;
    @Basic
    @Column(name = "codigocolor",nullable = false) //header
    private String codigocolor;
    @Basic
    @Column(name = "notificaciones",nullable = false)
    private int notificaciones;
    @Basic
    @Column(name = "imagendefondo") //background
    private String imagendefondo;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sede;
    @Basic
    @Column(name = "tipodeusuario_idtipodeusuario")
    private Integer tipodeusuarioIdtipodeusuario;
    @Basic
    @Column(name = "zonahoraria",nullable = false)
    private String zonahoraria;


}
