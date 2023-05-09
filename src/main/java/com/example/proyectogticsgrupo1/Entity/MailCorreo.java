package com.example.proyectogticsgrupo1.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "mensajes")

public class MailCorreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idmensaje", nullable = false)
    private Integer idMensaje;


    @Column(name ="asunto", nullable = false)
    private String asunto;

    @Column(name ="descripcion", nullable = false)
    private String descripcion;

    @Column(name ="correodestino", nullable = false)
    private String correodestino;

    @ManyToOne
    @JoinColumn(name = "idusuariodestino")
    private Usuario usuarioDestino;

    @ManyToOne
    @JoinColumn(name = "idusuarioorigen")
    private Usuario usuarioOrigen;

    @Column(name ="fecha", nullable = false)
    private LocalDate fecha;

    @Column(name ="hora", nullable = false)
    private LocalTime hora;

    @Column(name ="correoorigen", nullable = false)
    private String correo;

    @Column(name ="password", nullable = false)
    private String password;

    /*private String password;

    private String descripcion;

    public Integer getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /* Atributos Originales
    private int idCorreo;
    private String correo;
    private String password;
    private String descripcion;*/

}
