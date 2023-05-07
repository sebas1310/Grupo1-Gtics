package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipohoracalendariodoctor")
public class Tipohoracalendariodoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipohoracalendariodoctor")
    private Integer idtipohoracalendariodoctor;

    @Column(name = "nombre")
    private String nombre;
}
