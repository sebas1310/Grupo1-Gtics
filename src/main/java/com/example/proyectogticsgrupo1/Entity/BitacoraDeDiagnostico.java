package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="bitacoradediagnostico")
public class BitacoraDeDiagnostico {


    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="idbitacoradediagnostico",nullable = false)
    private Integer idBitacoraDiagnostico;

    @Column(name="descripcion",nullable = false, length = 500)
    private String descripcion;

    @Column(name="fechayhora",nullable = false)
    private Timestamp fechayhora;

    @ManyToOne
    @JoinColumn(name = "idpaciente",nullable = false)
    private Paciente paciente;

}