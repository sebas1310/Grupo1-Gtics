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

    @Query(nativeQuery = true, value = "select fecha from eventocalendariodoctor where idtipohoracalendariodoctor = 3\n" +
            "and iddoctor = ?1 and fecha = current_date();")
    List<Eventocalendariodoctor> horasDeCitas(Integer iddoctor);

    @Query(nativeQuery = true, value = "select * from eventocalendariodoctor where fecha=?1 and iddoctor=?2 and idtipohoracalendariodoctor=1")
    List<Eventocalendariodoctor> calendarioFecha(LocalDate fecha, Integer iddoc);

    @Modifying
    @Query(nativeQuery = true, value = "insert into eventocalendariodoctor (idtipohoracalendariodoctor,fecha, horainicio, horafinal, \n" +
            " duracion, descripcion, iddoctor) values (?1,?2,?3,?4,?5,?6,?7)")
    void agregarEventoDoctor(Integer idtipocalendario,LocalDate fecha, LocalTime horainicio, LocalTime horafinal,
                             Integer duracion, String descripcion, Integer iddoctor);

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

    //-------------Misma semana------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 2\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaLunes( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 3\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMartes( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 4\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMiercoles( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 5\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaJueves( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 6\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaViernes( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 7\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaSabados( Integer id);

    //-------------Siguiente semana------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 2\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaLunes1( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 3\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMartes1( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 4\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMiercoles1( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 5\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaJueves1( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 6\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaViernes1( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 7\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaSabados1( Integer id);


    //-------------A 2 semanas------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 2\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaLunes2( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 3\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMartes2( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2 AND DAYOFWEEK(fecha) = 4\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMiercoles2( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2 AND DAYOFWEEK(fecha) = 5\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaJueves2( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 6\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaViernes2( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 7\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaSabados2( Integer id);


    //-------------A 3 semanas------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 2\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaLunes3( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 3\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMartes3( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 4\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMiercoles3( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 5\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaJueves3( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3 AND DAYOFWEEK(fecha) = 6\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaViernes3( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3 AND DAYOFWEEK(fecha) = 7\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaSabados3( Integer id);


    //-------------a 4 semanas------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4 AND DAYOFWEEK(fecha) = 2\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaLunes4( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4 AND DAYOFWEEK(fecha) = 3\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMartes4( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4 AND DAYOFWEEK(fecha) = 4\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaMiercoles4( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4 AND DAYOFWEEK(fecha) = 5\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaJueves4( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4 AND DAYOFWEEK(fecha) = 6\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaViernes4( Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM eventocalendariodoctor WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4  AND DAYOFWEEK(fecha) = 7\n " +
            "AND  DATE(fecha) > DATE(DATE_ADD(CURDATE(), INTERVAL 1 DAY))\n " +
            "and idtipohoracalendariodoctor = 1 and iddoctor= ?1 ")
    List<Eventocalendariodoctor> listaSabados4( Integer id);

    @Query(nativeQuery = true, value = "SELECT DATE_ADD(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + (-2 + ?1) WEEK, INTERVAL (WEEKDAY(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + ?1 WEEK) + 1) DAY) AS start_date;")
    java.sql.Date obtnerInicioSemana(Integer numSemana);

    /* @Query(nativeQuery = true, value ="SELECT\n" +
             "DATE_ADD(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) - (2) WEEK, INTERVAL (WEEKDAY(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + ?1 WEEK) + 7) DAY) AS end_date;\n" )
     Date obtenerFinSemana(Integer numSemxana);
     */
    @Query(nativeQuery = true, value = "SELECT DATE_ADD(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + (-2+ ?1) WEEK, INTERVAL (WEEKDAY(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + ?1 WEEK) + 7) DAY) AS end_date;")
    java.sql.Date obtenerFinSemana(Integer numSemana);

    @Query(nativeQuery = true, value = "SELECT MONTHNAME(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + ?1 WEEK) AS month_name;")
    String obtenerMes(Integer numWeek);




}

