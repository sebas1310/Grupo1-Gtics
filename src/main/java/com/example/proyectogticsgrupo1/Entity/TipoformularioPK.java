package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class TipoformularioPK implements Serializable {
    @Column(name = "idtipoformulario")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtipoformulario;
    @Column(name = "idtipodeusuario")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtipodeusuario;

    public int getIdtipoformulario() {
        return idtipoformulario;
    }

    public void setIdtipoformulario(int idtipoformulario) {
        this.idtipoformulario = idtipoformulario;
    }

    public int getIdtipodeusuario() {
        return idtipodeusuario;
    }

    public void setIdtipodeusuario(int idtipodeusuario) {
        this.idtipodeusuario = idtipodeusuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipoformularioPK that = (TipoformularioPK) o;

        if (idtipoformulario != that.idtipoformulario) return false;
        if (idtipodeusuario != that.idtipodeusuario) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtipoformulario;
        result = 31 * result + idtipodeusuario;
        return result;
    }
}
