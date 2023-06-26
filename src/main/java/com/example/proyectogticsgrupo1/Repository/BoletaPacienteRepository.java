package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.BoletaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoletaPacienteRepository extends JpaRepository<BoletaPaciente,Integer> {

    @Modifying
    @Query(value= "insert into boletapaciente (idpaciente,idcita,idseguro,monto) values (?1,?2,?3,?4) ",nativeQuery = true)
    void generarBoletaPacienteCita(Integer idPaciente, Integer idCita,Integer idSeguro, Float monto);

    @Query(value = "SELECT * FROM boletapaciente where idcita=?1", nativeQuery = true)
    BoletaPaciente getBoletaCita(Integer id);

    @Query(value = "SELECT * FROM boletapaciente where idpaciente=?1", nativeQuery = true)
    List<BoletaPaciente> boletaspagadas(Integer id);
}
