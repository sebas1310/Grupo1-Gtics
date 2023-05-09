package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;


@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer idusuario;

    @ManyToOne
    @JoinColumn(name = "idtipodeusuario")
    private Tipodeusuario tipodeusuario;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "dni")
    //@Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;

    @Column(name = "correo")
    private String correo;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "genero")
    private String genero;

    @Column(name = "celular")
    private String celular;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "estado_habilitado")
    private Integer estadohabilitado;

    @ManyToOne
    @JoinColumn(name = "sede_idsede")
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "especialidad_idespecialidad")
    private Especialidad especialidad;


    /*
    @Column(name = "foto")
    private Blob foto;*/

}
