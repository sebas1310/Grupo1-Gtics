package com.example.proyectogticsgrupo1.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idpaciente", nullable = false)
    private Integer idpaciente;

    @Column(name ="direccion", nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idestadopaciente")
    private EstadoPaciente estadoPaciente;

    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro seguro;


    @Column(name = "alergias", nullable = false)
    private String alergias;

    @Column(name = "consentimientos", nullable = false)
    private Integer consentimientos;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "condicion_enfermedad")
    private String condicionenfermedad;

}
