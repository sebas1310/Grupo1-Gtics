package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Eventocalendariodoctor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ideventocalendariodoctor")
    private int ideventocalendariodoctor;
    @Basic
    @Column(name = "idtipohoracalendariodoctor")
    private int idtipohoracalendariodoctor;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "horainicio")
    private Time horainicio;
    @Basic
    @Column(name = "horafinal")
    private Time horafinal;
    @Basic
    @Column(name = "duracion")
    private int duracion;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "iddoctor")
    private int iddoctor;

    public int getIdeventocalendariodoctor() {
        return ideventocalendariodoctor;
    }

    public void setIdeventocalendariodoctor(int ideventocalendariodoctor) {
        this.ideventocalendariodoctor = ideventocalendariodoctor;
    }

    public int getIdtipohoracalendariodoctor() {
        return idtipohoracalendariodoctor;
    }

    public void setIdtipohoracalendariodoctor(int idtipohoracalendariodoctor) {
        this.idtipohoracalendariodoctor = idtipohoracalendariodoctor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Time horainicio) {
        this.horainicio = horainicio;
    }

    public Time getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(Time horafinal) {
        this.horafinal = horafinal;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(int iddoctor) {
        this.iddoctor = iddoctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Eventocalendariodoctor that = (Eventocalendariodoctor) o;

        if (ideventocalendariodoctor != that.ideventocalendariodoctor) return false;
        if (idtipohoracalendariodoctor != that.idtipohoracalendariodoctor) return false;
        if (duracion != that.duracion) return false;
        if (iddoctor != that.iddoctor) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (horainicio != null ? !horainicio.equals(that.horainicio) : that.horainicio != null) return false;
        if (horafinal != null ? !horafinal.equals(that.horafinal) : that.horafinal != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ideventocalendariodoctor;
        result = 31 * result + idtipohoracalendariodoctor;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (horainicio != null ? horainicio.hashCode() : 0);
        result = 31 * result + (horafinal != null ? horafinal.hashCode() : 0);
        result = 31 * result + duracion;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + iddoctor;
        return result;
    }
}
