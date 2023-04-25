package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(TipoformularioPK.class)
public class Tipoformulario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipoformulario")
    private int idtipoformulario;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipodeusuario")
    private int idtipodeusuario;

    public int getIdtipoformulario() {
        return idtipoformulario;
    }

    public void setIdtipoformulario(int idtipoformulario) {
        this.idtipoformulario = idtipoformulario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

        Tipoformulario that = (Tipoformulario) o;

        if (idtipoformulario != that.idtipoformulario) return false;
        if (idtipodeusuario != that.idtipodeusuario) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtipoformulario;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + idtipodeusuario;
        return result;
    }
}
