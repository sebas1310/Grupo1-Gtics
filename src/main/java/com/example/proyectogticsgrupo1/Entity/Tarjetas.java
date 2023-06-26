package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tarjetas")
public class Tarjetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarjetas")
    private Integer idtarjetas;

    @Column(name = "numero")
    private String numero;

    @Column(name = "mes")
    private String mes;

    @Column(name = "anio")
    private String anio;


    @Column(name = "cvv")
    private String cvv;

}
