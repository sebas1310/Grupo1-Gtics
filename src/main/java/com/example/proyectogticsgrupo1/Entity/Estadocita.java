package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Estadocita {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idestadocita")
    private int idestadocita;
    @Basic
    @Column(name = "nombre")
    private String nombre;

    public int getIdestadocita() {
        return idestadocita;
    }

    public void setIdestadocita(int idestadocita) {
        this.idestadocita = idestadocita;
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

        Estadocita that = (Estadocita) o;

        if (idestadocita != that.idestadocita) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idestadocita;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
