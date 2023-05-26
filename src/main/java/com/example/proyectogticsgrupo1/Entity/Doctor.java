package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
public class Doctor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddoctor")
    private int iddoctor;
    @Basic
    @Column(name = "idespecialidad")
    private int idespecialidad;
    @Basic
    @Column(name = "idsede")
    private int idsede;
    @Basic
    @Column(name = "cmp")
    private int cmp;
    @Basic
    @Column(name = "formacion")
    private String formacion;
    @Basic
    @Column(name = "rne")
    private int rne;
    @Basic
    @Column(name = "capacitaciones")
    private String capacitaciones;
    @Basic
    @Column(name = "idusuario")
    private int idusuario;
    @Basic
    @Column(name = "zoom")
    private String zoom;
    @Basic
    @Column(name = "consultorio")
    private String consultorio;

    public int getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(int iddoctor) {
        this.iddoctor = iddoctor;
    }

    public int getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(int idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public int getCmp() {
        return cmp;
    }

    public void setCmp(int cmp) {
        this.cmp = cmp;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public int getRne() {
        return rne;
    }

    public void setRne(int rne) {
        this.rne = rne;
    }

    public String getCapacitaciones() {
        return capacitaciones;
    }

    public void setCapacitaciones(String capacitaciones) {
        this.capacitaciones = capacitaciones;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (iddoctor != doctor.iddoctor) return false;
        if (idespecialidad != doctor.idespecialidad) return false;
        if (idsede != doctor.idsede) return false;
        if (cmp != doctor.cmp) return false;
        if (rne != doctor.rne) return false;
        if (idusuario != doctor.idusuario) return false;
        if (formacion != null ? !formacion.equals(doctor.formacion) : doctor.formacion != null) return false;
        if (capacitaciones != null ? !capacitaciones.equals(doctor.capacitaciones) : doctor.capacitaciones != null)
            return false;
        if (zoom != null ? !zoom.equals(doctor.zoom) : doctor.zoom != null) return false;
        if (consultorio != null ? !consultorio.equals(doctor.consultorio) : doctor.consultorio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iddoctor;
        result = 31 * result + idespecialidad;
        result = 31 * result + idsede;
        result = 31 * result + cmp;
        result = 31 * result + (formacion != null ? formacion.hashCode() : 0);
        result = 31 * result + rne;
        result = 31 * result + (capacitaciones != null ? capacitaciones.hashCode() : 0);
        result = 31 * result + idusuario;
        result = 31 * result + (zoom != null ? zoom.hashCode() : 0);
        result = 31 * result + (consultorio != null ? consultorio.hashCode() : 0);
        return result;
    }
}
