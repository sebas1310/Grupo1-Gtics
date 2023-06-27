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
    @Column(name = "modo")
    private String modo;
    @Basic
    @Column(name = "codigocolor") //header
    private String codigocolor;
    @Basic
    @Column(name = "notificaciones")
    private int notificaciones;
    @Basic
    @Column(name = "imagendefondo") //background
    private String imagendefondo;
    @Basic
    @Column(name = "tipodeusuario_idtipodeusuario")
    private Integer tipodeusuarioIdtipodeusuario;
    @Basic
    @Column(name = "zonahoraria")
    private String zonahoraria;


}
