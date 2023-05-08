package com.example.proyectogticsgrupo1.Entity;
import java.sql.Blob;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

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

    @Column(name = "nombres", nullable = false)
    @NotBlank
    @Size(max = 45, message = "El nombre no puede tener más de 45 caracteres")
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    @NotBlank
    @Size(max = 45, message = "El apellido no puede tener más de 45 caracteres")
    private String apellidos;

    @Column(name = "dni", nullable = false)
    @NotBlank
    @Size(max = 8, message = "El DNI no puede tener más de 8 caracteres")
    private String dni;


    @Column(name = "correo", nullable = false)
    @NotBlank
    @Size(max = 45, message = "El correo no puede tener más de 45 caracteres")
    private String correo;

    @Column(name = "contrasena", nullable = false)
    @NotBlank
    private String contrasena;

    @Column(name = "genero", nullable = false)
    @NotBlank
    private String genero;

    @Column(name = "celular", nullable = false)
    @NotBlank
    @Size(max = 9, message = "El celular no puede tener más de 45 caracteres")
    private String celular;

    @Column(name = "edad", nullable = false)
    @Positive
    @Digits(integer = 3, fraction = 0)
    private Integer edad;

    @Column(name = "estado_habilitado", nullable = false)
    private Integer estadohabilitado;

    @ManyToOne
    @JoinColumn(name = "sede_idsede")
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "especialidad_idespecialidad")
    private Especialidad especialidad;


    /*@Column(name = "foto")
    private Blob foto;*/

}