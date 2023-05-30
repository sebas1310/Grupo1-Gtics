package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.DTO.DiasProximosDoctor;
import com.example.proyectogticsgrupo1.Entity.Eventocalendariodoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;


public interface EventocalendariodoctorRepository extends JpaRepository<Eventocalendariodoctor,Integer> {
    @Query(nativeQuery = true, value = "select * from eventocalendariodoctor where iddoctor=?1 and idtipohoracalendariodoctor=1")
    List<Eventocalendariodoctor> calendarioPorDoctor(Integer iddoc);

    @Query(nativeQuery = true, value = "select * from eventocalendariodoctor where idtipohoracalendariodoctor=1 and fecha >= CURDATE()")
    List<Eventocalendariodoctor> calendarioDoctorDisponible();

    @Query(nativeQuery = true, value = "select * from eventocalendariodoctor where fecha=?1 and iddoctor=?2 and idtipohoracalendariodoctor=1")
    List<Eventocalendariodoctor> calendarioFecha(LocalDate fecha, Integer iddoc);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE eventocalendariodoctor SET idtipohoracalendariodoctor = 3, descripcion='ocupado' WHERE iddoctor=?1 and fecha=?2 and horainicio=?3")
    void cambiarEstadoCalendario(Integer iddoc, LocalDate fecha, LocalTime horain);

    @Query(nativeQuery = true, value =
            "SELECT DAYNAME(fecha) AS dia, DATE_FORMAT(horainicio, '%H:%i') AS inicio, DATE_FORMAT(horafinal, '%H:%i') AS fin \n" +
            "FROM eventocalendariodoctor \n" +
            "WHERE idtipohoracalendariodoctor=1 AND iddoctor=?1 AND fecha >= CURDATE() \n" +
            "ORDER BY fecha ASC\n" +
            "LIMIT 2; \n")
    List<DiasProximosDoctor> getDiasProx(Integer id);

    //lunes
    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM eventocalendariodoctor\n" +
            "WHERE fecha >= CURDATE() AND DAYOFWEEK(fecha) = 2 and idtipohoracalendariodoctor=1 and iddoctor=?1")
    List<Eventocalendariodoctor> listalunes(Integer id);

/*
    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM eventocalendariodoctor\n" +
            "WHERE fecha >= CURDATE() AND DAYOFWEEK(fecha) = 2 and idtipohoracalendariodoctor=1 and iddoctor=:id")
    List<Eventocalendariodoctor> listalunes(@Param("id") Integer id);
*/

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM eventocalendariodoctor\n" +
            "WHERE fecha >= CURDATE() AND DAYOFWEEK(fecha) = 3 and idtipohoracalendariodoctor=1 and iddoctor=?1")
    List<Eventocalendariodoctor> listaMartes(Integer id);

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM eventocalendariodoctor\n" +
            "WHERE fecha >= CURDATE() AND DAYOFWEEK(fecha) = 4 and idtipohoracalendariodoctor=1 and iddoctor=?1")
    List<Eventocalendariodoctor> listaMiercoles(Integer id);

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM eventocalendariodoctor\n" +
            "WHERE fecha >= CURDATE() AND DAYOFWEEK(fecha) = 5 and idtipohoracalendariodoctor=1 and iddoctor=?1")
    List<Eventocalendariodoctor> listaJueves(Integer id);

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM eventocalendariodoctor\n" +
            "WHERE fecha >= CURDATE() AND DAYOFWEEK(fecha) = 6 and idtipohoracalendariodoctor=1 and iddoctor=?1")
    List<Eventocalendariodoctor> listaViernes(Integer id);

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM eventocalendariodoctor\n" +
            "WHERE fecha >= CURDATE() AND DAYOFWEEK(fecha) = 7 and idtipohoracalendariodoctor=1 and iddoctor=?1")
    List<Eventocalendariodoctor> listaSabado(Integer id);


}

