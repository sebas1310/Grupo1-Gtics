package com.example.proyectogticsgrupo1.DTO;

import java.time.LocalDate;

public interface PacientesAtendidos {

    Integer getIDCita();

    LocalDate getUltimaFecha();

    Integer getIDPaciente();

    String getNombrePaciente();

}
