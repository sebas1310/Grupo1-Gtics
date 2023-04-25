package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Doctor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddoctor")
    private int iddoctor;
    @ManyToOne
    @JoinColumn(name = "idespecialidad")
    private Especialidad especialidad;
    @ManyToOne
    @JoinColumn (name = "idsede")
    private Sede sede;
    @Basic
    @Column(name = "cmp")
    private int cmp;
    @Basic
    @Column(name = "formacion")
    private String formacion;
    @Basic
    @Column(name = "rne")
    private int rne;

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Sede getSede() {
        return sede;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Basic
    @Column(name = "capacitaciones")
    private String capacitaciones;
    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    public int getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(int iddoctor) {
        this.iddoctor = iddoctor;
    }

    public int getCmp() {
        return cmp;
    }

    public void setCmp(int cmp) {
        this.cmp = cmp;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public int getRne() {
        return rne;
    }

    public void setRne(int rne) {
        this.rne = rne;
    }

    public String getCapacitaciones() {
        return capacitaciones;
    }

    public void setCapacitaciones(String capacitaciones) {
        this.capacitaciones = capacitaciones;
    }
}
