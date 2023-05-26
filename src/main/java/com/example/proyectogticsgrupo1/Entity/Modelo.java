package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Modelo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmodelo")
    private int idmodelo;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "preguntas")
    private String preguntas;
    @Basic
    @Column(name = "formulario")
    private Byte formulario;
    @Basic
    @Column(name = "informe")
    private Byte informe;
    @Basic
    @Column(name = "idtipodeusuario")
    private int idtipodeusuario;
    @Basic
    @Column(name = "idespecialidad")
    private Integer idespecialidad;
    @Basic
    @Column(name = "idcita")
    private Integer idcita;
    @Basic
    @Column(name = "nro_inputs")
    private Integer nroInputs;
    @Basic
    @Column(name = "cuestionario")
    private Byte cuestionario;

    public int getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(int idmodelo) {
        this.idmodelo = idmodelo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(String preguntas) {
        this.preguntas = preguntas;
    }

    public Byte getFormulario() {
        return formulario;
    }

    public void setFormulario(Byte formulario) {
        this.formulario = formulario;
    }

    public Byte getInforme() {
        return informe;
    }

    public void setInforme(Byte informe) {
        this.informe = informe;
    }

    public int getIdtipodeusuario() {
        return idtipodeusuario;
    }

    public void setIdtipodeusuario(int idtipodeusuario) {
        this.idtipodeusuario = idtipodeusuario;
    }

    public Integer getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(Integer idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public Integer getIdcita() {
        return idcita;
    }

    public void setIdcita(Integer idcita) {
        this.idcita = idcita;
    }

    public Integer getNroInputs() {
        return nroInputs;
    }

    public void setNroInputs(Integer nroInputs) {
        this.nroInputs = nroInputs;
    }

    public Byte getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(Byte cuestionario) {
        this.cuestionario = cuestionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Modelo modelo = (Modelo) o;

        if (idmodelo != modelo.idmodelo) return false;
        if (idtipodeusuario != modelo.idtipodeusuario) return false;
        if (nombre != null ? !nombre.equals(modelo.nombre) : modelo.nombre != null) return false;
        if (preguntas != null ? !preguntas.equals(modelo.preguntas) : modelo.preguntas != null) return false;
        if (formulario != null ? !formulario.equals(modelo.formulario) : modelo.formulario != null) return false;
        if (informe != null ? !informe.equals(modelo.informe) : modelo.informe != null) return false;
        if (idespecialidad != null ? !idespecialidad.equals(modelo.idespecialidad) : modelo.idespecialidad != null)
            return false;
        if (idcita != null ? !idcita.equals(modelo.idcita) : modelo.idcita != null) return false;
        if (nroInputs != null ? !nroInputs.equals(modelo.nroInputs) : modelo.nroInputs != null) return false;
        if (cuestionario != null ? !cuestionario.equals(modelo.cuestionario) : modelo.cuestionario != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idmodelo;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (preguntas != null ? preguntas.hashCode() : 0);
        result = 31 * result + (formulario != null ? formulario.hashCode() : 0);
        result = 31 * result + (informe != null ? informe.hashCode() : 0);
        result = 31 * result + idtipodeusuario;
        result = 31 * result + (idespecialidad != null ? idespecialidad.hashCode() : 0);
        result = 31 * result + (idcita != null ? idcita.hashCode() : 0);
        result = 31 * result + (nroInputs != null ? nroInputs.hashCode() : 0);
        result = 31 * result + (cuestionario != null ? cuestionario.hashCode() : 0);
        return result;
    }
}
