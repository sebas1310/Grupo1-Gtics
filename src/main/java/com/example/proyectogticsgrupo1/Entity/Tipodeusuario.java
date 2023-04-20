package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
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


}
