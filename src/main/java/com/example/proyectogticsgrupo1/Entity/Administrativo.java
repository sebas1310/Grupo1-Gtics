package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "administrativo")
public class Administrativo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idadministrativo", nullable = false)
    private int idadministrativo;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede idsede;
    @ManyToOne
    @JoinColumn(name = "idespecialidad")
    private Especialidad idespecialidad;
    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario idusuario;
}
