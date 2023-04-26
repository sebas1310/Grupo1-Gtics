package com.example.proyectogticsgrupo1.Entity;

<<<<<<< HEAD
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
=======

import com.mysql.cj.jdbc.Blob;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idpaciente", nullable = false)
    private Integer idpaciente;

    @Column(name ="direccion", nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idestadopaciente")
    private EstadoPaciente estadoPaciente;

    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro seguro;


    @Column(name = "condicion_enfermedad", length = 100)
    private String condicionEnfermedad;

    @Column(name = "alergias",length = 45)
    private String alergias;

    @Column(name = "consentimientos", nullable = false)
    private Integer consentimientos;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;


>>>>>>> doctor
}
