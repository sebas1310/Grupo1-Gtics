package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.DTO.PacientesAtendidos;
import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE paciente_idpaciente = ?1 AND fecha < CURRENT_DATE() ORDER BY fecha ASC ;\n")
    List<Cita> citaPorPaciente(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE paciente_idpaciente = ?1 AND fecha=?2")
    List<Cita> citasRepetidasValidacion(Integer id, LocalDate fecha);

    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE fecha= CURRENT_DATE and paciente_idpaciente=?1 ORDER BY fecha ASC")
    List<Cita> citasHoy(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE WEEKDAY(fecha) = 3 AND YEARWEEK(fecha, 1) = YEARWEEK(CURDATE(), 1)")
    List<Cita> citasJueves();

    @Query(nativeQuery = true, value = "SELECT * FROM cita where fecha>=current_date() and paciente_idpaciente=?1 and idtipocita=2")
    List<Cita> citasPorPagar(Integer id);
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO cita (idsede, idespecialidad, fecha, horainicio, horafinal, duracion, idtipocita, idseguro, idestadocita, paciente_idpaciente, doctor_iddoctor) VALUES (?1, ?2, ?3, ?4, ?5, ?6,?7,?8,?9,?10,?11)")
    void agengedarcita(Integer idsede, Integer idespecialidad,LocalDate fecha, LocalTime horini, LocalTime horafin,Integer duracion, Integer idtipocita, Integer idseguro, Integer idestadoctia, Integer idpac, Integer iddoc);

    @Query(value = "SELECT p.idpaciente, c.paciente_idpaciente, c.fecha\n" +
            "FROM paciente p\n" +
            "         JOIN cita c ON p.idpaciente= c.paciente_idpaciente", nativeQuery = true)
    List<Cita> citasporpaciente(String citas);

    List<Cita> findByPacienteAndFechaAfterOrderByFechaAsc(Paciente paciente, LocalDate fechaactual);


    /*@Query(value= "select * from cita where doctor_iddoctor= ?1 and concat(fecha, ' ' , horainicio , ' ', horafinal) <=current_timestamp()\n" +
            "order by concat(fecha, ' ' , horainicio ) DESC",nativeQuery = true)
    List<Cita> pacientesAtendidosPorDoctor(Integer idDoctor);*/


    @Query(value = "SELECT c.idcita AS 'IDCita', MAX(fecha) AS 'UltimaFecha', c.paciente_idpaciente AS 'IDPaciente', CONCAT(u.nombres, ' ', u.apellidos) AS 'NombrePaciente', t.nombre AS 'TipoCita' " +
            "FROM cita c " +
            "INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente " +
            "INNER JOIN usuario u ON p.idusuario = u.idusuario " +
            "INNER JOIN tipocita t ON c.idtipocita = t.idtipocita " +
            "WHERE c.doctor_iddoctor = ?1 AND fecha <= CURRENT_DATE() " +
            "GROUP BY c.idcita, c.paciente_idpaciente, CONCAT(u.nombres, ' ', u.apellidos), t.nombre " +
            "ORDER BY MAX(fecha)", nativeQuery = true)
    List<PacientesAtendidos> pacientesAtendidosPorDoctor(Integer idDoctor);



    @Query(value= "select * from cita where paciente_idpaciente= ?1 and doctor_iddoctor = ?2 ",nativeQuery = true)
    List<Cita> citasPorPaciente(Integer idPaciente,Integer idDoctor);

    @Query(value= "select * from cita where idcita = ?1 ",nativeQuery = true)
    Cita buscarCitaPorId (Integer idCita);

    @Query(value= "select * from cita where doctor_iddoctor= ?1 and concat(fecha, ' ' , horainicio , ' ', horafinal) >=current_timestamp()\n" +
            "order by concat(fecha, ' ' , horainicio )",nativeQuery = true)
    List<Cita> buscarCitasAgendadasDoctor (Integer idDoctor);

    @Query(value= "select * from cita where fecha= ?1 and horainicio = ?2 ",nativeQuery = true)
    Cita citaAgendada (LocalDate fecha, LocalTime horainicio);

}


