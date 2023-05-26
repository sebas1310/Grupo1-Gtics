package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Mensaje {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmensaje")
    private int idmensaje;
    @Basic
    @Column(name = "asunto")
    private String asunto;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "correodestino")
    private String correodestino;
    @Basic
    @Column(name = "idusuariodestino")
    private Integer idusuariodestino;
    @Basic
    @Column(name = "idusuarioorigen")
    private Integer idusuarioorigen;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "hora")
    private Time hora;
    @Basic
    @Column(name = "correoorigen")
    private String correoorigen;
    @Basic
    @Column(name = "password")
    private String password;

    public int getIdmensaje() {
        return idmensaje;
    }

    public void setIdmensaje(int idmensaje) {
        this.idmensaje = idmensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreodestino() {
        return correodestino;
    }

    public void setCorreodestino(String correodestino) {
        this.correodestino = correodestino;
    }

    public Integer getIdusuariodestino() {
        return idusuariodestino;
    }

    public void setIdusuariodestino(Integer idusuariodestino) {
        this.idusuariodestino = idusuariodestino;
    }

    public Integer getIdusuarioorigen() {
        return idusuarioorigen;
    }

    public void setIdusuarioorigen(Integer idusuarioorigen) {
        this.idusuarioorigen = idusuarioorigen;
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

    public String getCorreoorigen() {
        return correoorigen;
    }

    public void setCorreoorigen(String correoorigen) {
        this.correoorigen = correoorigen;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mensaje mensaje = (Mensaje) o;

        if (idmensaje != mensaje.idmensaje) return false;
        if (asunto != null ? !asunto.equals(mensaje.asunto) : mensaje.asunto != null) return false;
        if (descripcion != null ? !descripcion.equals(mensaje.descripcion) : mensaje.descripcion != null) return false;
        if (correodestino != null ? !correodestino.equals(mensaje.correodestino) : mensaje.correodestino != null)
            return false;
        if (idusuariodestino != null ? !idusuariodestino.equals(mensaje.idusuariodestino) : mensaje.idusuariodestino != null)
            return false;
        if (idusuarioorigen != null ? !idusuarioorigen.equals(mensaje.idusuarioorigen) : mensaje.idusuarioorigen != null)
            return false;
        if (fecha != null ? !fecha.equals(mensaje.fecha) : mensaje.fecha != null) return false;
        if (hora != null ? !hora.equals(mensaje.hora) : mensaje.hora != null) return false;
        if (correoorigen != null ? !correoorigen.equals(mensaje.correoorigen) : mensaje.correoorigen != null)
            return false;
        if (password != null ? !password.equals(mensaje.password) : mensaje.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idmensaje;
        result = 31 * result + (asunto != null ? asunto.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (correodestino != null ? correodestino.hashCode() : 0);
        result = 31 * result + (idusuariodestino != null ? idusuariodestino.hashCode() : 0);
        result = 31 * result + (idusuarioorigen != null ? idusuarioorigen.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (hora != null ? hora.hashCode() : 0);
        result = 31 * result + (correoorigen != null ? correoorigen.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
