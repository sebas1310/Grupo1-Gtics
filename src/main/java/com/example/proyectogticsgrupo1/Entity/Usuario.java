package com.example.proyectogticsgrupo1.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", nullable = false)
    private Integer idusuario;

    @ManyToOne
    @JoinColumn(name = "idtipodeusuario", nullable = false)
    private Tipodeusuario tipodeusuario;

    @Column(name = "nombres", nullable = false)
    @NotBlank
    @Size(min = 3,max = 100, message = "El nombre no puede tener mas de 100 caracteres")
    private String nombres;
    @NotBlank
    @Size(min = 3,max = 100, message = "El apellido no puede tener mas de 100 caracteres")
    private String apellidos;

    @Positive
    @Digits(integer = 8, fraction = 0, message = "El DNI debe tener 8 digitos")
    private String dni;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Positive
    @Digits(integer = 9, fraction = 0, message = "El celular debe tener 9 digitos")
    private String celular;

    @NotNull
    @Positive
    @Digits(integer=2, fraction = 0)
    private Integer edad;

    @Column(name = "estado_habilitado", nullable = false)
    private Integer estadohabilitado;

    @ManyToOne
    @JoinColumn(name = "sede_idsede")
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "especialidad_idespecialidad")
    private Especialidad especialidad;

    @Column(name = "sueldo")
    private Double sueldo;

}