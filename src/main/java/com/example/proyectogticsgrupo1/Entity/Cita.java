package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "cita")
public class Cita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcita", nullable = false)
    private Integer idcita;

    @ManyToOne
    @JoinColumn(name = "idsede",  nullable = false)
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "idespecialidad", nullable = false)
    private Especialidad especialidad;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Column(name = "horainicio", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime horainicio;

    @Column(name = "horafinal", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime horafinal;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @ManyToOne
    @JoinColumn(name = "idtipocita",  nullable = false)
    private TipoCita tipoCita;

    @ManyToOne
    @JoinColumn(name = "idseguro",  nullable = false)
    private Seguro seguro;

    @ManyToOne
    @JoinColumn(name = "idestadocita",  nullable = false)
    private EstadoCita estadoCita;

    @ManyToOne
    @JoinColumn(name = "paciente_idpaciente",  nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "doctor_iddoctor", nullable = false)
    private Doctor doctor;

    //private Integer flagReceta;
}





