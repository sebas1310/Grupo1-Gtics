package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Administrativo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idadministrativo")
    private int idadministrativo;
    @Basic
    @Column(name = "idsede")
    private int idsede;
    @Basic
    @Column(name = "idespecialidad")
    private int idespecialidad;
    @Basic
    @Column(name = "idusuario")
    private int idusuario;

    public int getIdadministrativo() {
        return idadministrativo;
    }

    public void setIdadministrativo(int idadministrativo) {
        this.idadministrativo = idadministrativo;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public int getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(int idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrativo that = (Administrativo) o;

        if (idadministrativo != that.idadministrativo) return false;
        if (idsede != that.idsede) return false;
        if (idespecialidad != that.idespecialidad) return false;
        if (idusuario != that.idusuario) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idadministrativo;
        result = 31 * result + idsede;
        result = 31 * result + idespecialidad;
        result = 31 * result + idusuario;
        return result;
    }
}
