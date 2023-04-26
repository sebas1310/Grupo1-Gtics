package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Especialidad that = (Especialidad) o;
        return idespecialidad == that.idespecialidad && Double.compare(that.costo, costo) == 0 && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idespecialidad, nombre, costo);
    }
}
