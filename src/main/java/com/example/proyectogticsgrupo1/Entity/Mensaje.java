
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
@Table(name = "mensaje")

public class Mensaje {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column (name = "idmensaje")
    private Integer idMensaje;

    @Basic
    @Column(name = "asunto")
    private String asunto;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    @Basic
    @Column(name = "correodestino")
    private String correodestino;

    @ManyToOne
    @JoinColumn(name = "idusuariodestino",  nullable = false)
    private Usuario usuariodestino;

    @ManyToOne
    @JoinColumn(name = "idusuarioorigen",  nullable = false)
    private Usuario usuarioorigen;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime hora;

}