package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
<<<<<<< HEAD

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
=======
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "especialidad")
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idespecialidad", nullable = false)
    private Integer idespecialidad;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "costo", nullable = false)
    private Double costo;

>>>>>>> doctor
}
