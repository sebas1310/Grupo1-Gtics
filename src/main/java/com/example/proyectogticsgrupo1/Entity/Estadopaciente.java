package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "estadopaciente")
public class Estadopaciente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idestadopaciente", nullable = false)
    private int idestadopaciente;

    @Column(name = "nombre", nullable = false)
    private String nombre;

}
