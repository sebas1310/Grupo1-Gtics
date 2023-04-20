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

    @Column(name ="direccion", nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idestadopaciente")
    private EstadoPaciente estadoPaciente;

    @ManyToOne
    @JoinColumn(name = "idseguro")
    private Seguro seguro;


    @Column(name = "alergias", nullable = false)
    private String alergias;

    @Column(name = "consetimientos", nullable = false)
    private Integer consentimiento;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;


}
