package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.BoletaDoctor;
import com.example.proyectogticsgrupo1.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoletaDoctorRepository extends JpaRepository<BoletaDoctor,Integer> {

    @Query(value= "select * from boletadoctor where idcita = ?1 ",nativeQuery = true)
    BoletaDoctor buscarBoletaDoctorCita(Integer idCita);

    @Modifying
    @Query(value= "insert into boletadoctor (idcita,idpaciente,iddoctor,monto) values (?1,?2,?3,?4) ",nativeQuery = true)
    void generarBoletaDoctorCita(Integer idCita, Integer idPaciente, Integer idDoctor, Float monto);

}
