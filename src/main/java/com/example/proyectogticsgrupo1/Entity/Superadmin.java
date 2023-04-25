package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "superadmin")
public class Superadmin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idsuperadmin", nullable = false)
    private int idsuperadmin;
    @Basic
    @Column(name = "empresa", nullable = false)
    private String empresa;
    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario idusuario;

}
