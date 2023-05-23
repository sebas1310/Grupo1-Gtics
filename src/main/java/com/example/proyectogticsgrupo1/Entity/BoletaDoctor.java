package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="boletadoctor")

public class BoletaDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idboletadoctor",  nullable = false)
    private Integer idBoletaDoc;

    @ManyToOne
    @JoinColumn(name = "idcita",  nullable = false)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "idpaciente",  nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "iddoctor",  nullable = false)
    private Doctor doctor;

    @Column(name = "monto",  nullable = false)
    private Float monto;

}