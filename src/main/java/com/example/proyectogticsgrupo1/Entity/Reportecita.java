package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Reportecita {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idreportecita")
    private int idreportecita;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "idcita")
    private int idcita;

    public int getIdreportecita() {
        return idreportecita;
    }

    public void setIdreportecita(int idreportecita) {
        this.idreportecita = idreportecita;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdcita() {
        return idcita;
    }

    public void setIdcita(int idcita) {
        this.idcita = idcita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reportecita that = (Reportecita) o;

        if (idreportecita != that.idreportecita) return false;
        if (idcita != that.idcita) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idreportecita;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + idcita;
        return result;
    }
}
