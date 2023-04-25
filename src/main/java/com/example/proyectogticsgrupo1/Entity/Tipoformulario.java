package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tipoformulario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipoformulario")
    private int idtipoformulario;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipodeusuario")
    private int idtipodeusuario;

    public int getIdtipoformulario() {
        return idtipoformulario;
    }

    public void setIdtipoformulario(int idtipoformulario) {
        this.idtipoformulario = idtipoformulario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdtipodeusuario() {
        return idtipodeusuario;
    }

    public void setIdtipodeusuario(int idtipodeusuario) {
        this.idtipodeusuario = idtipodeusuario;
    }


}
