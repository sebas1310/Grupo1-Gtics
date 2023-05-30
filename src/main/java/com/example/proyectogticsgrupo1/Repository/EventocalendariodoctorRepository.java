package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.DTO.DiasProximosDoctor;
import com.example.proyectogticsgrupo1.Entity.Eventocalendariodoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    @Query(nativeQuery = true, value = "select * from eventocalendariodoctor where iddoctor=?1 order by fecha")
    List<Eventocalendariodoctor> eventosCalendarioDoctor(Integer iddoc);

}

