package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE paciente SET alergias = ?1 WHERE idpaciente = ?2")
    void modificarAlergia(String alergias, Integer idpaciente);


    @Query(value="select * from paciente where idpaciente= ?1", nativeQuery = true)
    Paciente buscarPacientePorID (Integer idPaciente);
}
