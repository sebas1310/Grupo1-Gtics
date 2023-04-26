package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
<<<<<<< HEAD

import java.util.Objects;

@Entity
public class Tipodeusuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipodeusuario")
    private int idtipodeusuario;
    @Basic
    @Column(name = "nombre")
    private String nombre;

    public int getIdtipodeusuario() {
        return idtipodeusuario;
    }

    public void setIdtipodeusuario(int idtipodeusuario) {
        this.idtipodeusuario = idtipodeusuario;
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
        Tipodeusuario that = (Tipodeusuario) o;
        return idtipodeusuario == that.idtipodeusuario && Objects.equals(nombre, that.nombre);
    }
=======
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipodeusuario")
public class Tipodeusuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipodeusuario", nullable = false)
    private Integer tipodeusuario;

    @Column(name = "nombre")
    private String nombre;

>>>>>>> doctor

}
