package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public interface PacienteRepository extends JpaRepository<Paciente,Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE paciente SET alergias = ?1 WHERE idpaciente = ?2")
    void modificarAlergia(String alergias, Integer idpaciente);
}
