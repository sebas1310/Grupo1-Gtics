package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Estadopaciente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idestadopaciente")
    private int idestadopaciente;
    @Basic
    @Column(name = "nombre")
    private String nombre;

    public int getIdestadopaciente() {
        return idestadopaciente;
    }

    public void setIdestadopaciente(int idestadopaciente) {
        this.idestadopaciente = idestadopaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estadopaciente that = (Estadopaciente) o;
        return idestadopaciente == that.idestadopaciente && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idestadopaciente, nombre);
    }
}
