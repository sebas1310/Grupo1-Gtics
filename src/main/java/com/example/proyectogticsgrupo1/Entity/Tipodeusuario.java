package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
@Getter
@Setter
@Entity
@Table(name = "tipodeusuario")

public class Tipodeusuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipodeusuario", nullable = false)
    private Integer idtipodeusuario;

    @Column(name = "nombre")
    private String nombre;

}

