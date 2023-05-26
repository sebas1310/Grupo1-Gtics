package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modelo_json", schema = "bdclinica", catalog = "")
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

        if (id != that.id) return false;
        if (idtipodeusuario != that.idtipodeusuario) return false;
        if (nombrePlantilla != null ? !nombrePlantilla.equals(that.nombrePlantilla) : that.nombrePlantilla != null)
            return false;
        if (datos != null ? !datos.equals(that.datos) : that.datos != null) return false;
        if (idespecialidad != null ? !idespecialidad.equals(that.idespecialidad) : that.idespecialidad != null)
            return false;
        if (formulario != null ? !formulario.equals(that.formulario) : that.formulario != null) return false;
        if (informe != null ? !informe.equals(that.informe) : that.informe != null) return false;
        if (cuestionario != null ? !cuestionario.equals(that.cuestionario) : that.cuestionario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombrePlantilla != null ? nombrePlantilla.hashCode() : 0);
        result = 31 * result + (datos != null ? datos.hashCode() : 0);
        result = 31 * result + (idespecialidad != null ? idespecialidad.hashCode() : 0);
        result = 31 * result + idtipodeusuario;
        result = 31 * result + (formulario != null ? formulario.hashCode() : 0);
        result = 31 * result + (informe != null ? informe.hashCode() : 0);
        result = 31 * result + (cuestionario != null ? cuestionario.hashCode() : 0);
        return result;
    }
}
