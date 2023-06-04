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
@Table(name = "notificaciones", schema = "bdclinicag1_v2")
public class Notificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotificaciones")
    private Integer idnotificaciones;

    @ManyToOne
    @JoinColumn(name = "idusuariodestino")
    private Usuario usuario;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "titulo")
    private String titulo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private LocalTime hora;
}
