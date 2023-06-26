package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "datos_json", schema = "bdclinicag1_v2", catalog = "")
public class DatosJsonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre_plantilla",  nullable = false)
    private String nombre_plantilla;

    @Column(name = "datos_llenos")
    private Object datosLlenos;

    @ManyToOne
    @JoinColumn(name = "idusuario",  nullable = false)
    private Usuario usuario;

    @Column(name = "modelo_json_id", nullable = false)
    private int modelo_json_id;

    @ManyToOne
    @JoinColumn(name = "cita_idcita")
    private Cita cita;


}
