package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

   @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.tipodeusuario.idtipodeusuario = 4")
    List<Paciente> test();

    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.nombres = ?", nativeQuery = true)
    List<Paciente> buscarPaciente();



}
=======
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository

public interface PacienteRepository extends JpaRepository<Paciente,Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE paciente SET alergias = ?1 WHERE idpaciente = ?2")
    void modificarAlergia(String alergias, Integer idpaciente);


    @Query(value="select * from paciente where idpaciente= ?1", nativeQuery = true)
    Paciente buscarPacientePorID (Integer idPaciente);
}
>>>>>>> doctor
