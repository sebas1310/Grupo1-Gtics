package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Tipocita {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipocita")
    private int idtipocita;
    @Basic
    @Column(name = "nombre")
    private String nombre;

    public int getIdtipocita() {
        return idtipocita;
    }

    public void setIdtipocita(int idtipocita) {
        this.idtipocita = idtipocita;
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

        Tipocita tipocita = (Tipocita) o;

        if (idtipocita != tipocita.idtipocita) return false;
        if (nombre != null ? !nombre.equals(tipocita.nombre) : tipocita.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtipocita;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
