package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bitacoradediagnostico")
public class BitacoraDeDiagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbitacoradediagnostico")
    private Integer idbitacoradediagnostico;

    @Column(name = "descripcion")
    private String descripcion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechayhora;

    @ManyToOne
    @JoinColumn(name = "idpaciente")
    private Paciente paciente;
}