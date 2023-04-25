package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "seguro")
public class Seguro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idseguro", nullable = false)
    private int idseguro;
    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Basic
    @Column(name = "coaseguro", nullable = false)
    private double coaseguro;
    @Basic
    @Column(name = "comisiondoctor", nullable = false)
    private double comisiondoctor;

}
