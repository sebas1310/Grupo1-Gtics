package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seguro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idseguro")
    private int idseguro;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "coaseguro")
    private double coaseguro;
    @Basic
    @Column(name = "comisiondoctor")
    private double comisiondoctor;

    public int getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(int idseguro) {
        this.idseguro = idseguro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCoaseguro() {
        return coaseguro;
    }

    public void setCoaseguro(double coaseguro) {
        this.coaseguro = coaseguro;
    }

    public double getComisiondoctor() {
        return comisiondoctor;
    }

    public void setComisiondoctor(double comisiondoctor) {
        this.comisiondoctor = comisiondoctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seguro seguro = (Seguro) o;

        if (idseguro != seguro.idseguro) return false;
        if (Double.compare(seguro.coaseguro, coaseguro) != 0) return false;
        if (Double.compare(seguro.comisiondoctor, comisiondoctor) != 0) return false;
        if (nombre != null ? !nombre.equals(seguro.nombre) : seguro.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idseguro;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        temp = Double.doubleToLongBits(coaseguro);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(comisiondoctor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
