package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE paciente_idpaciente = ?1 AND fecha < CURRENT_DATE() ORDER BY fecha ASC ;\n")
    List<Cita> citaPorPaciente(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE paciente_idpaciente = ?1 AND fecha=?2")
    List<Cita> citasRepetidasValidacion(Integer id, LocalDate fecha);

    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE fecha= CURRENT_DATE")
    List<Cita> citasHoy(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE WEEKDAY(fecha) = 3 AND YEARWEEK(fecha, 1) = YEARWEEK(CURDATE(), 1)")
    List<Cita> citasJueves();

    @Query(nativeQuery = true, value = "SELECT * FROM cita where fecha>=current_date() and paciente_idpaciente=?1 and idtipocita=2")
    List<Cita> citasPorPagar(Integer id);
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO cita (idsede, idespecialidad, fecha, horainicio, horafinal, duracion, idtipocita, idseguro, idestadocita, paciente_idpaciente, doctor_iddoctor) VALUES (?1, ?2, ?3, ?4, ?5, ?6,?7,?8,?9,?10,?11)")
    void agengedarcita(Integer idsede, Integer idespecialidad,LocalDate fecha, LocalTime horini, LocalTime horafin,Integer duracion, Integer idtipocita, Integer idseguro, Integer idestadoctia, Integer idpac, Integer iddoc);
}
