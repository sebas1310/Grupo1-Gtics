package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query(value= "select * from cita where doctor_iddoctor= '2' and concat(fecha, ' ' , horainicio , ' ', horafinal) <=current_timestamp()\n" +
            "order by concat(fecha, ' ' , horainicio ) DESC",nativeQuery = true)
    List<Cita> pacientesAtendidosPorDoctor(Integer idDoctor);

    @Query(value= "select * from cita where paciente_idpaciente= ?1 and doctor_iddoctor = '2'",nativeQuery = true)
    List<Cita> citasPorPaciente(Integer idPaciente);

    @Query(value= "select * from cita where idcita = ?1 ",nativeQuery = true)
    Cita buscarCitaPorId (Integer idCita);

    @Query(value= "select * from cita where doctor_iddoctor= '2' and concat(fecha, ' ' , horainicio , ' ', horafinal) >=current_timestamp()\n" +
            "order by concat(fecha, ' ' , horainicio )",nativeQuery = true)
    List<Cita> buscarCitasAgendadasDoctor (Integer idDoctor);




}
