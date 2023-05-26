package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tabla_datos_llenos", schema = "bdclinica", catalog = "")
public class TablaDatosLlenos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "datos_llenos_inputs")
    private String datosLlenosInputs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatosLlenosInputs() {
        return datosLlenosInputs;
    }

    public void setDatosLlenosInputs(String datosLlenosInputs) {
        this.datosLlenosInputs = datosLlenosInputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TablaDatosLlenos that = (TablaDatosLlenos) o;

        if (id != that.id) return false;
        if (datosLlenosInputs != null ? !datosLlenosInputs.equals(that.datosLlenosInputs) : that.datosLlenosInputs != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (datosLlenosInputs != null ? datosLlenosInputs.hashCode() : 0);
        return result;
    }
}
