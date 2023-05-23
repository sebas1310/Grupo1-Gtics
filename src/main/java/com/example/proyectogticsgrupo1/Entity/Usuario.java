package com.example.proyectogticsgrupo1.Entity;
import java.sql.Blob;


import jakarta.persistence.*;
<<<<<<< HEAD
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
=======
<<<<<<< HEAD
>>>>>>> origin

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
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

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

<<<<<<< HEAD
}
=======
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

=======
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

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

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
>>>>>>> doctor

}
>>>>>>> origin
