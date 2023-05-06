package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.tipodeusuario.idtipodeusuario = 4")
    List<Paciente> test();


    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.nombres = ?", nativeQuery = true)
    List<Paciente> buscarPaciente();

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       JOIN usuario u on p.idusuario = u.idusuario where c.idsede = 1", nativeQuery = true)
    List<Paciente> listarPacienteporSede(int idsede);






}