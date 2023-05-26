package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Datos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddatos")
    private int iddatos;
    @Basic
    @Column(name = "respuestas")
    private String respuestas;
    @Basic
    @Column(name = "idmodelo")
    private int idmodelo;
    @Basic
    @Column(name = "idusuario")
    private int idusuario;
    @Basic
    @Column(name = "llenado")
    private Byte llenado;

    public int getIddatos() {
        return iddatos;
    }

    public void setIddatos(int iddatos) {
        this.iddatos = iddatos;
    }

    public String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(String respuestas) {
        this.respuestas = respuestas;
    }

    public int getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(int idmodelo) {
        this.idmodelo = idmodelo;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public Byte getLlenado() {
        return llenado;
    }

    public void setLlenado(Byte llenado) {
        this.llenado = llenado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Datos datos = (Datos) o;

        if (iddatos != datos.iddatos) return false;
        if (idmodelo != datos.idmodelo) return false;
        if (idusuario != datos.idusuario) return false;
        if (respuestas != null ? !respuestas.equals(datos.respuestas) : datos.respuestas != null) return false;
        if (llenado != null ? !llenado.equals(datos.llenado) : datos.llenado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iddatos;
        result = 31 * result + (respuestas != null ? respuestas.hashCode() : 0);
        result = 31 * result + idmodelo;
        result = 31 * result + idusuario;
        result = 31 * result + (llenado != null ? llenado.hashCode() : 0);
        return result;
    }
}
