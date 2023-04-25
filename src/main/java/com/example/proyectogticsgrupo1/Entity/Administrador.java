package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(AdministradorPK.class)
public class Administrador {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idadministrador")
    private int idadministrador;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idsede")
    private int idsede;
    @Basic
    @Column(name = "idusuario")
    private int idusuario;

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

        Administrador that = (Administrador) o;

        if (idadministrador != that.idadministrador) return false;
        if (idsede != that.idsede) return false;
        if (idusuario != that.idusuario) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idadministrador;
        result = 31 * result + idsede;
        result = 31 * result + idusuario;
        return result;
    }
}
