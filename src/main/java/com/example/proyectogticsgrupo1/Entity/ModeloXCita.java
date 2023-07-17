package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "modelo_x_cita")
public class ModeloXCita {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;


    @Column(name = "idmodelo_fk")
    private Integer idmodelofk;

    @Column(name = "idpaciente_fk")
    private Integer idpacientefk;

    @Column(name = "idcita_fk")
    private Integer idcitafk;

    @Column(name = "mostrar_automatico")
    private Integer mostrar_automatico;

    @Column(name = "fill")
    private Integer fill;
}
