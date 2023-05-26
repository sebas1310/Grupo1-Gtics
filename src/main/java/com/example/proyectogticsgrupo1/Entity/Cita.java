package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Cita {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idcita")
    private int idcita;
    @Basic
    @Column(name = "idsede")
    private int idsede;
    @Basic
    @Column(name = "idespecialidad")
    private int idespecialidad;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "horainicio")
    private Time horainicio;
    @Basic
    @Column(name = "horafinal")
    private Time horafinal;
    @Basic
    @Column(name = "duracion")
    private int duracion;
    @Basic
    @Column(name = "idtipocita")
    private int idtipocita;
    @Basic
    @Column(name = "idseguro")
    private int idseguro;
    @Basic
    @Column(name = "idestadocita")
    private int idestadocita;
    @Basic
    @Column(name = "paciente_idpaciente")
    private int pacienteIdpaciente;
    @Basic
    @Column(name = "doctor_iddoctor")
    private int doctorIddoctor;

    public int getIdcita() {
        return idcita;
    }

    public void setIdcita(int idcita) {
        this.idcita = idcita;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public int getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(int idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Time horainicio) {
        this.horainicio = horainicio;
    }

    public Time getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(Time horafinal) {
        this.horafinal = horafinal;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getIdtipocita() {
        return idtipocita;
    }

    public void setIdtipocita(int idtipocita) {
        this.idtipocita = idtipocita;
    }

    public int getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(int idseguro) {
        this.idseguro = idseguro;
    }

    public int getIdestadocita() {
        return idestadocita;
    }

    public void setIdestadocita(int idestadocita) {
        this.idestadocita = idestadocita;
    }

    public int getPacienteIdpaciente() {
        return pacienteIdpaciente;
    }

    public void setPacienteIdpaciente(int pacienteIdpaciente) {
        this.pacienteIdpaciente = pacienteIdpaciente;
    }

    public int getDoctorIddoctor() {
        return doctorIddoctor;
    }

    public void setDoctorIddoctor(int doctorIddoctor) {
        this.doctorIddoctor = doctorIddoctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cita cita = (Cita) o;

        if (idcita != cita.idcita) return false;
        if (idsede != cita.idsede) return false;
        if (idespecialidad != cita.idespecialidad) return false;
        if (duracion != cita.duracion) return false;
        if (idtipocita != cita.idtipocita) return false;
        if (idseguro != cita.idseguro) return false;
        if (idestadocita != cita.idestadocita) return false;
        if (pacienteIdpaciente != cita.pacienteIdpaciente) return false;
        if (doctorIddoctor != cita.doctorIddoctor) return false;
        if (fecha != null ? !fecha.equals(cita.fecha) : cita.fecha != null) return false;
        if (horainicio != null ? !horainicio.equals(cita.horainicio) : cita.horainicio != null) return false;
        if (horafinal != null ? !horafinal.equals(cita.horafinal) : cita.horafinal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idcita;
        result = 31 * result + idsede;
        result = 31 * result + idespecialidad;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (horainicio != null ? horainicio.hashCode() : 0);
        result = 31 * result + (horafinal != null ? horafinal.hashCode() : 0);
        result = 31 * result + duracion;
        result = 31 * result + idtipocita;
        result = 31 * result + idseguro;
        result = 31 * result + idestadocita;
        result = 31 * result + pacienteIdpaciente;
        result = 31 * result + doctorIddoctor;
        return result;
    }
}
