package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "paciente")
public class Paciente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idpaciente")
    private int idpaciente;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @ManyToOne
    @JoinColumn(name = "idestadopaciente")
    private Estadopaciente idestadopaciente;
    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro idseguro;
    @Basic
    @Column(name = "alergias")
    private String alergias;
    @Basic
    @Column(name = "consentimientos")
    private int consentimientos;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @Basic
    @Column(name = "condicion_enfermedad")
    private String condicion_enfermedad;

    @Basic
    @Column(name = "poliza")
    private String poliza;


}