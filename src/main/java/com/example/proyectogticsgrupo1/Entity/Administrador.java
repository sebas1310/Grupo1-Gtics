package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "administrador")
public class Administrador {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idadministrador", nullable = false)
    private int idadministrador;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede idsede;
    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario idusuario;

}
