package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class AdministradorPK implements Serializable {
    @Column(name = "idadministrador")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idadministrador;
    @Column(name = "idsede")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsede;

    public int getIdadministrador() {
        return idadministrador;
    }

    public void setIdadministrador(int idadministrador) {
        this.idadministrador = idadministrador;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdministradorPK that = (AdministradorPK) o;

        if (idadministrador != that.idadministrador) return false;
        if (idsede != that.idsede) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idadministrador;
        result = 31 * result + idsede;
        return result;
    }
}
