package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "datos_json", schema = "bdclinica", catalog = "")
public class DatosJson {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre_plantilla")
    private String nombrePlantilla;
    @Basic
    @Column(name = "datos_llenos")
    private Object datosLlenos;
    @Basic
    @Column(name = "idusuario")
    private int idusuario;
    @Basic
    @Column(name = "modelo_json_id")
    private int modeloJsonId;
    @Basic
    @Column(name = "cita_idcita")
    private Integer citaIdcita;

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

    public Object getDatosLlenos() {
        return datosLlenos;
    }

    public void setDatosLlenos(Object datosLlenos) {
        this.datosLlenos = datosLlenos;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getModeloJsonId() {
        return modeloJsonId;
    }

    public void setModeloJsonId(int modeloJsonId) {
        this.modeloJsonId = modeloJsonId;
    }

    public Integer getCitaIdcita() {
        return citaIdcita;
    }

    public void setCitaIdcita(Integer citaIdcita) {
        this.citaIdcita = citaIdcita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatosJson datosJson = (DatosJson) o;

        if (id != datosJson.id) return false;
        if (idusuario != datosJson.idusuario) return false;
        if (modeloJsonId != datosJson.modeloJsonId) return false;
        if (nombrePlantilla != null ? !nombrePlantilla.equals(datosJson.nombrePlantilla) : datosJson.nombrePlantilla != null)
            return false;
        if (datosLlenos != null ? !datosLlenos.equals(datosJson.datosLlenos) : datosJson.datosLlenos != null)
            return false;
        if (citaIdcita != null ? !citaIdcita.equals(datosJson.citaIdcita) : datosJson.citaIdcita != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombrePlantilla != null ? nombrePlantilla.hashCode() : 0);
        result = 31 * result + (datosLlenos != null ? datosLlenos.hashCode() : 0);
        result = 31 * result + idusuario;
        result = 31 * result + modeloJsonId;
        result = 31 * result + (citaIdcita != null ? citaIdcita.hashCode() : 0);
        return result;
    }
}
