package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query(value = "SELECT p.idpaciente, c.paciente_idpaciente, c.fecha\n" +
        "FROM paciente p\n" +
        "         JOIN cita c ON p.idpaciente= c.paciente_idpaciente", nativeQuery = true)
    List<Cita> citasporpaciente(String citas);

    List<Cita> findByPacienteAndFechaAfterOrderByFechaAsc(Paciente paciente, LocalDate fechaactual);




}