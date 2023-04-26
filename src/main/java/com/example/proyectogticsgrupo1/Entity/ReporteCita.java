package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reportecita")
public class ReporteCita {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreportecita", nullable = false)
    private Integer idReporteCita;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;


    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @OneToOne
    @JoinColumn(name = "idcita", nullable = false)
    private Cita cita;

}