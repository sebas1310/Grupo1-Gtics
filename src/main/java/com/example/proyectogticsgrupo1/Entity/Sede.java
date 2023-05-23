package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
<<<<<<< HEAD
>>>>>>> origin

import java.sql.Blob;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sede")
public class Sede {
    @Id
    @GeneratedValue
    @Column(name = "idsede")
    private Integer idsede;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "coordenadas", nullable = false)
    private String coordenadas;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    /*
    @Column(name = "foto",nullable = false)
    private Blob foto;*/

<<<<<<< HEAD
}
=======
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
=======
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@Entity
@Table(name = "sede")
public class Sede {
    @Id
    @GeneratedValue
    @Column(name = "idsede")
    private Integer idsede;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "coordenadas", nullable = false)
    private String coordenadas;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    /*
    @Column(name = "foto",nullable = false)
    private Blob foto;*/

>>>>>>> doctor
}
>>>>>>> origin
