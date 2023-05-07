package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "boletapaciente")
public class BoletaPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idboletapaciente")
    private Integer idboletapaciente;

    @ManyToOne
    @JoinColumn(name = "idpaciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "idcita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro seguro;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

}
