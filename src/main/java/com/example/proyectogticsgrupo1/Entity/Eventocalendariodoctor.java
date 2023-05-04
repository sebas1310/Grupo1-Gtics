package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "eventocalendariodoctor")
public class Eventocalendariodoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ideventocalendariodoctor")
    private Integer ideventocalendariodoctor;

    @ManyToOne
    @JoinColumn(name = "idtipohoracalendariodoctor")
    private Tipohoracalendariodoctor tipohoracalendariodoctor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Column(name = "horainicio", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime horainicio;

    @Column(name = "horafinal", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime horafinal;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "iddoctor")
    private Doctor doctor;



}
