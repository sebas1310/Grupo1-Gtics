package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Notificaciones {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idnotificaciones")
    private int idnotificaciones;
    @Basic
    @Column(name = "idusuariodestino")
    private Integer idusuariodestino;
    @Basic
    @Column(name = "contenido")
    private String contenido;
    @Basic
    @Column(name = "titulo")
    private String titulo;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "hora")
    private Time hora;

    public int getIdnotificaciones() {
        return idnotificaciones;
    }

    public void setIdnotificaciones(int idnotificaciones) {
        this.idnotificaciones = idnotificaciones;
    }

    public Integer getIdusuariodestino() {
        return idusuariodestino;
    }

    public void setIdusuariodestino(Integer idusuariodestino) {
        this.idusuariodestino = idusuariodestino;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notificaciones that = (Notificaciones) o;

        if (idnotificaciones != that.idnotificaciones) return false;
        if (idusuariodestino != null ? !idusuariodestino.equals(that.idusuariodestino) : that.idusuariodestino != null)
            return false;
        if (contenido != null ? !contenido.equals(that.contenido) : that.contenido != null) return false;
        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (hora != null ? !hora.equals(that.hora) : that.hora != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idnotificaciones;
        result = 31 * result + (idusuariodestino != null ? idusuariodestino.hashCode() : 0);
        result = 31 * result + (contenido != null ? contenido.hashCode() : 0);
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (hora != null ? hora.hashCode() : 0);
        return result;
    }
}
