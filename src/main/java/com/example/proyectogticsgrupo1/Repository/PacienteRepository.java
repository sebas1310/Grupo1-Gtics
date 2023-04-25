package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

   @Query("SELECT p FROM Paciente p JOIN p.usuario u WHERE u.tipodeusuario.idtipodeusuario = 4")
    List<Paciente> test();



}