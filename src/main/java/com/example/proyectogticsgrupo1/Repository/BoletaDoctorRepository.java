package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.DTO.IngresosEgresos;
import com.example.proyectogticsgrupo1.Entity.BoletaDoctor;
import com.example.proyectogticsgrupo1.Entity.Cita;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoletaDoctorRepository extends JpaRepository<BoletaDoctor,Integer> {

    @Query(value= "select * from boletadoctor where idcita = ?1 ",nativeQuery = true)
    BoletaDoctor buscarBoletaDoctorCita(Integer idCita);

    @Query(value= "select * from boletadoctor where iddoctor = ?1 ",nativeQuery = true)
    List<BoletaDoctor> buscarBoletaPorDoctor(Integer idDoc);

    @Query(value= "select * from boletadoctor where iddoctor = ?1  and idcita= ?2",nativeQuery = true)
    List<BoletaDoctor> buscarBoletaPorDoctorPorCita(Integer idDoc, Integer idCita);

    @Modifying
    @Query(value= "insert into boletadoctor (idcita,idpaciente,idseguro,iddoctor,monto) values (?1,?2,?3,?4,?5) ",nativeQuery = true)
    void generarBoletaDoctorCita(Integer idCita, Integer idPaciente, Integer idSeguro, Integer idDoctor, Float monto);


    @Query(value = "SELECT DATE_FORMAT(cita.fecha, '%Y-%m') AS mes,\n" +
            "       MONTHNAME(cita.fecha) AS nombre_mes,\n" +
            "       AVG(boletadoctor.monto) AS promedio_monto_doctor,\n" +
            "       AVG(boletapaciente.monto) AS promedio_monto_paciente\n" +
            "FROM boletadoctor\n" +
            "JOIN boletapaciente ON boletadoctor.idcita = boletapaciente.idcita\n" +
            "JOIN cita ON boletadoctor.idcita = cita.idcita\n" +
            "GROUP BY DATE_FORMAT(cita.fecha, '%Y-%m'), 2\n" +
            "ORDER BY FIELD(nombre_mes, 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'), mes;\n", nativeQuery = true)
    List<IngresosEgresos> IngresosEgresosDTO();

    @Modifying
    @Query(value= "delete from boletadoctor where idcita = ?1 ",nativeQuery = true)
    void delBoletaDoctor(Integer idcita);


}
