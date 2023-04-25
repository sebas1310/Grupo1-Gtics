package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Getter
@Setter
@Table(name = "ux/ui")
public class UxUi {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUX/UI")
    private int idUxUi;
    @Basic
    @Column(name = "modo")
    private String modo;
    @Basic
    @Column(name = "codigocolor")
    private String codigocolor;
    @Basic
    @Column(name = "notificaciones")
    private int notificaciones;
    @Basic
    @Column(name = "imagendefondo")
    private byte[] imagendefondo;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede idsede;

}
