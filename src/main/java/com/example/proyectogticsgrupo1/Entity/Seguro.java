package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "seguro")
public class Seguro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseguro")
    private Integer idseguro;

    @Column(name = "nombre")
    private String  nombre;

    @Column(name = "coaseguro")
    private Double coaseguro;

     @Column(name = "comisiondoctor")
    private Double comisiondoctor;
}

