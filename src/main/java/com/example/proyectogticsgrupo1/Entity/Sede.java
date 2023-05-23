package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Blob;


@Getter
@Setter
@Entity
@Table(name = "sede")
public class Sede implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "idsede")
    private Integer idsede;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "coordenadas", nullable = false)
    private String coordenadas;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    /*
    @Column(name = "foto",nullable = false)
    private Blob foto;*/

}
