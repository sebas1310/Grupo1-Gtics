package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "especialidad")
public class Especialidad {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idespecialidad", nullable = false)
    private int idespecialidad;
    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Basic
    @Column(name = "costo", nullable = false)
    private double costo;

}
