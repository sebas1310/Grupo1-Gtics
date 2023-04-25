package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class Sede {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idsede")
    private int idsede;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "coordenadas")
    private String coordenadas;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @Basic
    @Column(name = "foto")
    private byte[] foto;

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sede sede = (Sede) o;
        return idsede == sede.idsede && Objects.equals(nombre, sede.nombre) && Objects.equals(coordenadas, sede.coordenadas) && Objects.equals(direccion, sede.direccion) && Arrays.equals(foto, sede.foto);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(idsede, nombre, coordenadas, direccion);
        result = 31 * result + Arrays.hashCode(foto);
        return result;
    }
}
