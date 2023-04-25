package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Superadmin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idsuperadmin")
    private int idsuperadmin;
    @Basic
    @Column(name = "empresa")
    private String empresa;
    @Basic
    @Column(name = "idusuario")
    private int idusuario;

    public int getIdsuperadmin() {
        return idsuperadmin;
    }

    public void setIdsuperadmin(int idsuperadmin) {
        this.idsuperadmin = idsuperadmin;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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

        Superadmin that = (Superadmin) o;

        if (idsuperadmin != that.idsuperadmin) return false;
        if (idusuario != that.idusuario) return false;
        if (empresa != null ? !empresa.equals(that.empresa) : that.empresa != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idsuperadmin;
        result = 31 * result + (empresa != null ? empresa.hashCode() : 0);
        result = 31 * result + idusuario;
        return result;
    }
}
