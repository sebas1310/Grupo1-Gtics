
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
@Table(name = "mensajes")

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

}