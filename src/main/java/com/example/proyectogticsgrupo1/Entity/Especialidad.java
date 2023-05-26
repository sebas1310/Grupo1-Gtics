package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Especialidad {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idespecialidad")
    private int idespecialidad;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "costo")
    private double costo;
    @Basic
    @Column(name = "torre")
    private String torre;

    public int getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(int idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Especialidad that = (Especialidad) o;

        if (idespecialidad != that.idespecialidad) return false;
        if (Double.compare(that.costo, costo) != 0) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (torre != null ? !torre.equals(that.torre) : that.torre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idespecialidad;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        temp = Double.doubleToLongBits(costo);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (torre != null ? torre.hashCode() : 0);
        return result;
    }
}
