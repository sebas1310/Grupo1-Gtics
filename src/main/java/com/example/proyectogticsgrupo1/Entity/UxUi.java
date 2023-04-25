package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Getter
@Setter
@Table(name = "ux/ui", schema = "clinica", catalog = "")
public class UxUi {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUX/UI")
    private int idUxUi;
    @Basic
    @Column(name = "modo")
    private String modo;
    @Basic
    @Column(name = "codigocolor")
    private String codigocolor;
    @Basic
    @Column(name = "notificaciones")
    private int notificaciones;
    @Basic
    @Column(name = "imagendefondo")
    private byte[] imagendefondo;
    @Basic
    @Column(name = "idsede")
    private int idsede;

    public int getIdUxUi() {
        return idUxUi;
    }

    public void setIdUxUi(int idUxUi) {
        this.idUxUi = idUxUi;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getCodigocolor() {
        return codigocolor;
    }

    public void setCodigocolor(String codigocolor) {
        this.codigocolor = codigocolor;
    }

    public int getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(int notificaciones) {
        this.notificaciones = notificaciones;
    }

    public byte[] getImagendefondo() {
        return imagendefondo;
    }

    public void setImagendefondo(byte[] imagendefondo) {
        this.imagendefondo = imagendefondo;
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

        UxUi uxUi = (UxUi) o;

        if (idUxUi != uxUi.idUxUi) return false;
        if (notificaciones != uxUi.notificaciones) return false;
        if (idsede != uxUi.idsede) return false;
        if (modo != null ? !modo.equals(uxUi.modo) : uxUi.modo != null) return false;
        if (codigocolor != null ? !codigocolor.equals(uxUi.codigocolor) : uxUi.codigocolor != null) return false;
        if (!Arrays.equals(imagendefondo, uxUi.imagendefondo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUxUi;
        result = 31 * result + (modo != null ? modo.hashCode() : 0);
        result = 31 * result + (codigocolor != null ? codigocolor.hashCode() : 0);
        result = 31 * result + notificaciones;
        result = 31 * result + Arrays.hashCode(imagendefondo);
        result = 31 * result + idsede;
        return result;
    }
}
