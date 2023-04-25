package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Paciente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idpaciente")
    private int idpaciente;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @Basic
    @Column(name = "idestadopaciente")
    private int idestadopaciente;
    @Basic
    @Column(name = "idseguro")
    private int idseguro;
    @Basic
    @Column(name = "alergias")
    private String alergias;
    @Basic
    @Column(name = "consentimientos")
    private int consentimientos;
    @Basic
    @Column(name = "idusuario")
    private int idusuario;
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

    public int getIdestadopaciente() {
        return idestadopaciente;
    }

    public void setIdestadopaciente(int idestadopaciente) {
        this.idestadopaciente = idestadopaciente;
    }

    public int getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(int idseguro) {
        this.idseguro = idseguro;
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

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getCondicionEnfermedad() {
        return condicionEnfermedad;
    }

    public void setCondicionEnfermedad(String condicionEnfermedad) {
        this.condicionEnfermedad = condicionEnfermedad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paciente paciente = (Paciente) o;

        if (idpaciente != paciente.idpaciente) return false;
        if (idestadopaciente != paciente.idestadopaciente) return false;
        if (idseguro != paciente.idseguro) return false;
        if (consentimientos != paciente.consentimientos) return false;
        if (idusuario != paciente.idusuario) return false;
        if (direccion != null ? !direccion.equals(paciente.direccion) : paciente.direccion != null) return false;
        if (alergias != null ? !alergias.equals(paciente.alergias) : paciente.alergias != null) return false;
        if (condicionEnfermedad != null ? !condicionEnfermedad.equals(paciente.condicionEnfermedad) : paciente.condicionEnfermedad != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idpaciente;
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        result = 31 * result + idestadopaciente;
        result = 31 * result + idseguro;
        result = 31 * result + (alergias != null ? alergias.hashCode() : 0);
        result = 31 * result + consentimientos;
        result = 31 * result + idusuario;
        result = 31 * result + (condicionEnfermedad != null ? condicionEnfermedad.hashCode() : 0);
        return result;
    }
}
