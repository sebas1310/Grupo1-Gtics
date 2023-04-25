package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Formulariodoctor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idformulariodoctor")
    private int idformulariodoctor;
    @Basic
    @Column(name = "nombres")
    private String nombres;
    @Basic
    @Column(name = "apellidos")
    private String apellidos;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "dni")
    private String dni;
    @Basic
    @Column(name = "genero")
    private String genero;
    @Basic
    @Column(name = "celular")
    private String celular;
    @Basic
    @Column(name = "cmp")
    private Integer cmp;
    @Basic
    @Column(name = "rne")
    private Integer rne;
    @Basic
    @Column(name = "formacion")
    private String formacion;
    @Basic
    @Column(name = "capacitaciones")
    private String capacitaciones;
    @Basic
    @Column(name = "idtipoformulario")
    private int idtipoformulario;
    @Basic
    @Column(name = "idsede")
    private int idsede;
    @Basic
    @Column(name = "idespecialidad")
    private int idespecialidad;

    public int getIdformulariodoctor() {
        return idformulariodoctor;
    }

    public void setIdformulariodoctor(int idformulariodoctor) {
        this.idformulariodoctor = idformulariodoctor;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Integer getCmp() {
        return cmp;
    }

    public void setCmp(Integer cmp) {
        this.cmp = cmp;
    }

    public Integer getRne() {
        return rne;
    }

    public void setRne(Integer rne) {
        this.rne = rne;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public String getCapacitaciones() {
        return capacitaciones;
    }

    public void setCapacitaciones(String capacitaciones) {
        this.capacitaciones = capacitaciones;
    }

    public int getIdtipoformulario() {
        return idtipoformulario;
    }

    public void setIdtipoformulario(int idtipoformulario) {
        this.idtipoformulario = idtipoformulario;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Formulariodoctor that = (Formulariodoctor) o;

        if (idformulariodoctor != that.idformulariodoctor) return false;
        if (idtipoformulario != that.idtipoformulario) return false;
        if (idsede != that.idsede) return false;
        if (idespecialidad != that.idespecialidad) return false;
        if (nombres != null ? !nombres.equals(that.nombres) : that.nombres != null) return false;
        if (apellidos != null ? !apellidos.equals(that.apellidos) : that.apellidos != null) return false;
        if (correo != null ? !correo.equals(that.correo) : that.correo != null) return false;
        if (contrasena != null ? !contrasena.equals(that.contrasena) : that.contrasena != null) return false;
        if (dni != null ? !dni.equals(that.dni) : that.dni != null) return false;
        if (genero != null ? !genero.equals(that.genero) : that.genero != null) return false;
        if (celular != null ? !celular.equals(that.celular) : that.celular != null) return false;
        if (cmp != null ? !cmp.equals(that.cmp) : that.cmp != null) return false;
        if (rne != null ? !rne.equals(that.rne) : that.rne != null) return false;
        if (formacion != null ? !formacion.equals(that.formacion) : that.formacion != null) return false;
        if (capacitaciones != null ? !capacitaciones.equals(that.capacitaciones) : that.capacitaciones != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idformulariodoctor;
        result = 31 * result + (nombres != null ? nombres.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        result = 31 * result + (dni != null ? dni.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + (celular != null ? celular.hashCode() : 0);
        result = 31 * result + (cmp != null ? cmp.hashCode() : 0);
        result = 31 * result + (rne != null ? rne.hashCode() : 0);
        result = 31 * result + (formacion != null ? formacion.hashCode() : 0);
        result = 31 * result + (capacitaciones != null ? capacitaciones.hashCode() : 0);
        result = 31 * result + idtipoformulario;
        result = 31 * result + idsede;
        result = 31 * result + idespecialidad;
        return result;
    }
}
