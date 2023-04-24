package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tipoformulario")
public class Tipoformulario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipoformulario", nullable = false)
    private int idtipoformulario;
    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "idtipodeusuario", nullable = false)
    private Tipoformulario idtipodeusuario;


}