package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Pacientesporregistrar {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idformulariopaciente")
    private int idformulariopaciente;
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
    @Column(name = "edad")
    private Integer edad;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @Basic
    @Column(name = "genero")
    private String genero;
    @Basic
    @Column(name = "celular")
    private String celular;

    public int getIdformulariopaciente() {
        return idformulariopaciente;
    }

    public void setIdformulariopaciente(int idformulariopaciente) {
        this.idformulariopaciente = idformulariopaciente;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pacientesporregistrar that = (Pacientesporregistrar) o;

        if (idformulariopaciente != that.idformulariopaciente) return false;
        if (nombres != null ? !nombres.equals(that.nombres) : that.nombres != null) return false;
        if (apellidos != null ? !apellidos.equals(that.apellidos) : that.apellidos != null) return false;
        if (dni != null ? !dni.equals(that.dni) : that.dni != null) return false;
        if (edad != null ? !edad.equals(that.edad) : that.edad != null) return false;
        if (correo != null ? !correo.equals(that.correo) : that.correo != null) return false;
        if (contrasena != null ? !contrasena.equals(that.contrasena) : that.contrasena != null) return false;
        if (direccion != null ? !direccion.equals(that.direccion) : that.direccion != null) return false;
        if (genero != null ? !genero.equals(that.genero) : that.genero != null) return false;
        if (celular != null ? !celular.equals(that.celular) : that.celular != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idformulariopaciente;
        result = 31 * result + (nombres != null ? nombres.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (dni != null ? dni.hashCode() : 0);
        result = 31 * result + (edad != null ? edad.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + (celular != null ? celular.hashCode() : 0);
        return result;
    }
}
