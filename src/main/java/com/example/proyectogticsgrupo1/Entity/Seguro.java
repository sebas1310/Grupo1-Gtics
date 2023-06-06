package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "seguro")
public class Seguro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseguro")
    private Integer idseguro;
    @Size(min = 3,max = 45, message = "El nombre no puede tener mas de 45 caracteres")
    @Pattern(regexp = "[A-Za-zñÑáéíóúÁÉÍÓÚüÜ\\s]+", message = "El nombre solo puede contener letras")
    private String  nombre;
    @Positive
    @Max(value = 50)
    @Min(value = 50)
    private float coaseguro;
    @Positive
    @Max(value = 50)
    @Min(value = 0)
    private float comisiondoctor;
}

