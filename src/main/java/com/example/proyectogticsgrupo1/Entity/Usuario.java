package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Getter
@Setter
public class Usuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idusuario")
    private int idusuario;
    @Basic
    @Column(name = "idtipodeusuario")
    private int idtipodeusuario;
    @Basic
    @Column(name = "nombres")
    private String nombres;
    @Basic
    @Column(name = "apellidos")
    private String apellidos;
    @Basic
    @Column(name = "dni")
    private String dni;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "genero")
    private String genero;
    @Basic
    @Column(name = "foto")
    private byte[] foto;
    @Basic
    @Column(name = "celular")
    private String celular;
    @Basic
    @Column(name = "edad")
    private int edad;
    @Basic
    @Column(name = "estado")
    private int estado;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdtipodeusuario() {
        return idtipodeusuario;
    }

    public void setIdtipodeusuario(int idtipodeusuario) {
        this.idtipodeusuario = idtipodeusuario;
    }

    public String getNombres() {
        return nombres;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (idusuario != usuario.idusuario) return false;
        if (idtipodeusuario != usuario.idtipodeusuario) return false;
        if (edad != usuario.edad) return false;
        if (estado != usuario.estado) return false;
        if (nombres != null ? !nombres.equals(usuario.nombres) : usuario.nombres != null) return false;
        if (apellidos != null ? !apellidos.equals(usuario.apellidos) : usuario.apellidos != null) return false;
        if (dni != null ? !dni.equals(usuario.dni) : usuario.dni != null) return false;
        if (correo != null ? !correo.equals(usuario.correo) : usuario.correo != null) return false;
        if (contrasena != null ? !contrasena.equals(usuario.contrasena) : usuario.contrasena != null) return false;
        if (genero != null ? !genero.equals(usuario.genero) : usuario.genero != null) return false;
        if (!Arrays.equals(foto, usuario.foto)) return false;
        if (celular != null ? !celular.equals(usuario.celular) : usuario.celular != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idusuario;
        result = 31 * result + idtipodeusuario;
        result = 31 * result + (nombres != null ? nombres.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (dni != null ? dni.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(foto);
        result = 31 * result + (celular != null ? celular.hashCode() : 0);
        result = 31 * result + edad;
        result = 31 * result + estado;
        return result;
    }
}
