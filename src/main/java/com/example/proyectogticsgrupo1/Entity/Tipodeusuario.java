package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tipousuario")
public class Tipodeusuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipodeusuario", nullable = false)
    private int idtipodeusuario;
    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

}