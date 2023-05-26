package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tabla_titulos_inputs", schema = "bdclinica", catalog = "")
public class TablaTitulosInputs {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre_titulos")
    private String nombreTitulos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreTitulos() {
        return nombreTitulos;
    }

    public void setNombreTitulos(String nombreTitulos) {
        this.nombreTitulos = nombreTitulos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TablaTitulosInputs that = (TablaTitulosInputs) o;

        if (id != that.id) return false;
        if (nombreTitulos != null ? !nombreTitulos.equals(that.nombreTitulos) : that.nombreTitulos != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombreTitulos != null ? nombreTitulos.hashCode() : 0);
        return result;
    }
}
