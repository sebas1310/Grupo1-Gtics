package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idusuario", nullable = false)
    private int idusuario;
    @ManyToOne
    @JoinColumn(name = "idtipodeusuario", nullable = false)
    private Tipodeusuario idtipodeusuario;
    @Basic
    @Column(name = "nombres", nullable = false)
    private String nombres;
    @Basic
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    @Basic
    @Column(name = "dni", nullable = false)
    private String dni;
    @Basic
    @Column(name = "correo", nullable = false)
    private String correo;
    @Basic
    @Column(name = "contrasena", nullable = false)
    private String contrasena;
    @Basic
    @Column(name = "genero", nullable = false)
    private String genero;
    @Basic
    @Column(name = "foto", nullable = false)
    private byte[] foto;
    @Basic
    @Column(name = "celular", nullable = false)
    private String celular;
    @Basic
    @Column(name = "edad", nullable = false)
    private int edad;
    @Basic
    @Column(name = "estado", nullable = false)
    private int estado;

}
