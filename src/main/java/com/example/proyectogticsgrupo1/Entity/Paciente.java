package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Paciente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idpaciente")
    private int idpaciente;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @ManyToOne
    @JoinColumn(name = "idestadopaciente")
    private Estadopaciente idestadopaciente;
    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro idseguro;
    @Basic
    @Column(name = "alergias")
    private String alergias;
    @Basic
    @Column(name = "consentimientos")
    private int consentimientos;


    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Basic
    @Column(name = "condicion_enfermedad")
    private String condicionEnfermedad;

    public int getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public int getConsentimientos() {
        return consentimientos;
    }

    public void setConsentimientos(int consentimientos) {
        this.consentimientos = consentimientos;
    }

    public String getCondicionEnfermedad() {
        return condicionEnfermedad;
    }

    public Estadopaciente getIdestadopaciente() {
        return idestadopaciente;
    }

    public void setIdestadopaciente(Estadopaciente idestadopaciente) {
        this.idestadopaciente = idestadopaciente;
    }

    public Seguro getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(Seguro idseguro) {
        this.idseguro = idseguro;
    }

    public void setCondicionEnfermedad(String condicionEnfermedad) {
        this.condicionEnfermedad = condicionEnfermedad;
    }
}