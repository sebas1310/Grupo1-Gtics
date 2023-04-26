package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="estadopaciente")
public class EstadoPaciente {
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

        EstadoPaciente that = (EstadoPaciente) o;

        if (idestadopaciente != that.idestadopaciente) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idestadopaciente;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
