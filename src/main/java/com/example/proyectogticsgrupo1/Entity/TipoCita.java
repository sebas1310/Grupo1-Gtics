package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class TipoCita {
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

}
