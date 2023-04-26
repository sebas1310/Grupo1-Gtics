package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
<<<<<<< HEAD

import java.util.Objects;

@Entity
public class Seguro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idseguro")
    private int idseguro;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "coaseguro")
    private double coaseguro;
    @Basic
    @Column(name = "comisiondoctor")
    private double comisiondoctor;

    public int getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(int idseguro) {
        this.idseguro = idseguro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCoaseguro() {
        return coaseguro;
    }

    public void setCoaseguro(double coaseguro) {
        this.coaseguro = coaseguro;
    }

    public double getComisiondoctor() {
        return comisiondoctor;
    }

    public void setComisiondoctor(double comisiondoctor) {
        this.comisiondoctor = comisiondoctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seguro seguro = (Seguro) o;
        return idseguro == seguro.idseguro && Double.compare(seguro.coaseguro, coaseguro) == 0 && Double.compare(seguro.comisiondoctor, comisiondoctor) == 0 && Objects.equals(nombre, seguro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idseguro, nombre, coaseguro, comisiondoctor);
    }
=======
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seguro")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseguro")
    private Integer idseguro;

     @Column(name = "nombre")
    private String  nombre;

     @Column(name = "coaseguro")
    private Double coaseguro;

     @Column(name = "comisiondoctor")
    private Double comisiondoctor;
>>>>>>> doctor
}
