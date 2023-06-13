package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modelo_json", catalog = "")
public class ModeloJsonEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre_plantilla")
    private String nombrePlantilla;

    @Column(name = "datos")
    private Object datos;


    @ManyToOne
    @JoinColumn(name = "idespecialidad",  nullable = false)
    private Especialidad especialidad;

    @ManyToOne
    @JoinColumn(name = "idtipodeusuario",  nullable = false)
    private Tipodeusuario tipodeusuario;

    @Column(name = "formulario")
    private Byte formulario;

    @Column(name = "informe")
    private Byte informe;

    @Column(name = "cuestionario")
    private Byte cuestionario;

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    @Column(name = "habilitado")
    private int habilitado;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Tipodeusuario getTipodeusuario() {
        return tipodeusuario;
    }

    public void setTipodeusuario(Tipodeusuario tipodeusuario) {
        this.tipodeusuario = tipodeusuario;
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

    public Byte getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(Byte cuestionario) {
        this.cuestionario = cuestionario;
    }
}
