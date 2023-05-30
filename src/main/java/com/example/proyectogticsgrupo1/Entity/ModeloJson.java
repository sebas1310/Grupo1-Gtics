package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "modelo_json", schema = "bdclinicag1_v2", catalog = "")
public class ModeloJson {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre_plantilla")
    private String nombrePlantilla;
    @Basic
    @Column(name = "datos")
    private Object datos;
    @Basic
    @Column(name = "idespecialidad")
    private Integer idespecialidad;
    @Basic
    @Column(name = "idtipodeusuario")
    private int idtipodeusuario;
    @Basic
    @Column(name = "formulario")
    private Byte formulario;
    @Basic
    @Column(name = "informe")
    private Byte informe;
    @Basic
    @Column(name = "cuestionario")
    private Byte cuestionario;

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

    public Integer getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(Integer idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public int getIdtipodeusuario() {
        return idtipodeusuario;
    }

    public void setIdtipodeusuario(int idtipodeusuario) {
        this.idtipodeusuario = idtipodeusuario;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloJson that = (ModeloJson) o;
        return id == that.id && idtipodeusuario == that.idtipodeusuario && Objects.equals(nombrePlantilla, that.nombrePlantilla) && Objects.equals(datos, that.datos) && Objects.equals(idespecialidad, that.idespecialidad) && Objects.equals(formulario, that.formulario) && Objects.equals(informe, that.informe) && Objects.equals(cuestionario, that.cuestionario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombrePlantilla, datos, idespecialidad, idtipodeusuario, formulario, informe, cuestionario);
    }
}
