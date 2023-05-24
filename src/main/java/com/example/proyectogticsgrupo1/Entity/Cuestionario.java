package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Cuestionario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idcuestionario")
    private int idcuestionario;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "idpaciente")
    private int idpaciente;
    @Basic
    @Column(name = "iddoctor")
    private int iddoctor;
    @Basic
    @Column(name = "mostrarautomatico")
    private int mostrarautomatico;

    public int getIdcuestionario() {
        return idcuestionario;
    }

    public void setIdcuestionario(int idcuestionario) {
        this.idcuestionario = idcuestionario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public int getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(int iddoctor) {
        this.iddoctor = iddoctor;
    }

    public int getMostrarautomatico() {
        return mostrarautomatico;
    }

    public void setMostrarautomatico(int mostrarautomatico) {
        this.mostrarautomatico = mostrarautomatico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cuestionario that = (Cuestionario) o;

        if (idcuestionario != that.idcuestionario) return false;
        if (idpaciente != that.idpaciente) return false;
        if (iddoctor != that.iddoctor) return false;
        if (mostrarautomatico != that.mostrarautomatico) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idcuestionario;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + idpaciente;
        result = 31 * result + iddoctor;
        result = 31 * result + mostrarautomatico;
        return result;
    }
}
