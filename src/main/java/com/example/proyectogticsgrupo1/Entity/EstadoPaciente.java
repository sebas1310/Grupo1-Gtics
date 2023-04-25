package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estadopaciente")
public class EstadoPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idestadopaciente")
    private Integer idestadopaciente;

    @Column(name = "nombre")
    private String nombre;
}
