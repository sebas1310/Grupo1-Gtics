package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", nullable = false)
    private Integer idusuario;

    @ManyToOne
    @JoinColumn(name = "idtipodeusuario", nullable = false)
    private Tipodeusuario tipodeusuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apllidos;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "celular", nullable = false)
    private String celular;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "estado", nullable = false)
    private Integer estado;
    /*
    @Column(name = "foto")
    private Blob foto;*/

}
