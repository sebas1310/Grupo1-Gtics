package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "paciente")
public class Paciente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idpaciente", nullable = false)
    private int idpaciente;
    @Basic
    @Column(name = "direccion", nullable = false)
    private String direccion;
    @ManyToOne
    @JoinColumn(name = "idestadopaciente")
    private Estadopaciente idestadopaciente;
    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro idseguro;
    @Basic
    @Column(name = "alergias", nullable = false)
    private String alergias;
    @Basic
    @Column(name = "consentimientos", nullable = false)
    private int consentimientos;
    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario idusuario;
    @Basic
    @Column(name = "condicion_enfermedad", nullable = false)
    private String condicionEnfermedad;

}
