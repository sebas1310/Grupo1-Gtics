package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Getter
@Setter
@Table(name = "sede")
public class Sede {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idsede", nullable = false)
    private int idsede;
    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Basic
    @Column(name = "coordenadas", nullable = false)
    private String coordenadas;
    @Basic
    @Column(name = "direccion", nullable = false)
    private String direccion;
    @Basic
    @Column(name = "foto", nullable = false)
    private byte[] foto;

}
