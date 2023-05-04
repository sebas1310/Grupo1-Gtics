package com.example.proyectogticsgrupo1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE idpaciente = ?1 AND fecha < CURRENT_DATE();\n")
    List<Cita> citaPorPaciente(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO cita (costo, idsede, idpaciente, idespecialidad, iddoctor, fecha, horainicio, horafinal, duracion, idtipocita, idseguro, idestadocita) VALUES (?1, ?2, ?3, ?4, ?5, ?6,?7,?8,?9,?10, ?11,?12)")
    void agengedarcita(Double costo, Integer idsede, Integer idpaciente, Integer idespecialidad, Integer iddoctor, LocalDate fecha, LocalTime horini, LocalTime horafin,Integer duracion, Integer idtipocita, Integer idseguro, Integer idestadoctia );
}
