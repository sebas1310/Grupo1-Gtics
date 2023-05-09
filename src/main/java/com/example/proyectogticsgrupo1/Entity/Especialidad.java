package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "especialidad")
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
