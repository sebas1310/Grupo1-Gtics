package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "especialidad")
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idespecialidad", nullable = false)
    private Integer idespecialidad;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "costo", nullable = false)
    private Double costo;

}