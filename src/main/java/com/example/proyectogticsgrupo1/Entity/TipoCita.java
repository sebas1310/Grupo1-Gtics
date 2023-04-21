package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipocita")
public class TipoCita{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipocita", nullable = false)
    private Integer idtipocita;

    @Column(name = "nombre", nullable = false)
    private String nombre;
}
