package com.example.proyectogticsgrupo1.Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;
import java.util.Base64;

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
    @Size(min = 3,max = 45, message = "El nombre no puede tener mas de 100 caracteres")
    @Pattern(regexp = "[A-Za-zñÑáéíóúÁÉÍÓÚüÜ\\s]+", message = "El nombre solo puede contener letras")
    private String nombres;
    @NotBlank
    @Size(min = 3,max = 45, message = "El apellido no puede tener mas de 100 caracteres")
    @Pattern(regexp = "[A-Za-zñÑáéíóúÁÉÍÓÚüÜ\\s]+", message = "El nombre solo puede contener letras")
    private String apellidos;

    @Pattern(regexp = "^(?!00000000$)[0-9]{8}$", message = "El DNI debe tener 8 dígitos numéricos y no se acepta 00000000")
    private String dni;

    @Column(name = "correo", nullable = false)
    @Email(message = "El correo debe ser válido")
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "genero", nullable = false)
    @NotNull(message = "Debes seleccionar un género")
    private String genero;


    @Pattern(regexp = "^(9)[0-9]{8}$", message = "El número de celular debe empezar con '9' y tener 9 dígitos numéricos")
    private String celular;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 999, message = "La edad debe máximo tener 3 dígitos")
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