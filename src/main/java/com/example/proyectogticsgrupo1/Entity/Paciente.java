package com.example.proyectogticsgrupo1.Entity;


import com.mysql.cj.jdbc.Blob;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idpaciente", nullable = false)
    private Integer idpaciente;

    @Column(name ="nombre", nullable = false)
    private String nombre;

    @Column(name ="edad", nullable = false)
    private Integer edad;

    @Column(name ="direccion", nullable = false)
    private String direccion;

    @Column(name ="genero", nullable = false)
    private String genero;

    @Column(name ="dni", nullable = false)
    private String dni;

    @Column(name ="celular", nullable = false)
    private String celular;

    @ManyToOne
    @JoinColumn(name = "idestadopaciente")
    private EstadoPaciente estadoPaciente;

    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro seguro;


    @Column(name = "correo", nullable = false)
    private String correo;

    /*@Column(name = "foto", nullable = false)
    private Blob foto;*/

    @Column(name = "alergias", nullable = false)
    private String alergias;

    @Column(name = "consetimientos", nullable = false)
    private Integer consentimiento;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;


}
