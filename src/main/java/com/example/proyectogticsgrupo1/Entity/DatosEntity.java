package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "datos")
public class DatosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddatos")
    private int iddatos;
    @Basic
    @Column(name = "respuestas",nullable = false)
    private String respuestas;
    @ManyToOne
    @JoinColumn(name = "idmodelo")
    private ModeloEntity modeloEntity;
    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
    @Basic
    @Column(name = "llenado")
    private Byte llenado;


}
