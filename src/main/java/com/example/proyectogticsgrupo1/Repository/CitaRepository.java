package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.DTO.PacientesAtendidos;
import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import jakarta.transaction.Transactional;
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


    @Query(value = "SELECT c.idcita AS 'IDCita', fecha AS 'UltimaFecha', c.paciente_idpaciente AS 'IDPaciente',\n" +
            " CONCAT(u.nombres, ' ', u.apellidos) AS 'NombrePaciente', t.nombre AS 'TipoCita'\n" +
            "            FROM cita c\n" +
            "            INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente \n" +
            "            INNER JOIN usuario u ON p.idusuario = u.idusuario \n" +
            "            INNER JOIN tipocita t ON c.idtipocita = t.idtipocita \n" +
            "            WHERE c.doctor_iddoctor = ?1 AND fecha <= CURRENT_TIME() \n" +
            "            GROUP BY idpaciente \n" +
            "            ORDER BY fecha desc", nativeQuery = true)
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

    @Transactional
    @Modifying
    @Query(value= "update cita set idestadocita = ?1 where idcita = ?2 ",nativeQuery = true)
    void actualizarEstadoCita(Integer idestadocita,Integer idcita);

    @Query(value = "SELECT * FROM cita WHERE idsede = ?1 AND DATE_FORMAT(fecha, '%Y-%m-%d') = ?2", nativeQuery = true)
    List<Cita> citaPorSede(Integer idsede, String fecha);


    //-------------Misma semana------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 2\n" +

            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaLunes(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 3\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaMartes(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 4\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaMiercoles(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 5\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaJueves(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 6\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaViernes(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 0  AND DAYOFWEEK(fecha) = 7\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaSabados(Integer id);

    //-------------Siguiente semana------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 2\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaLunescitas1(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 3\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaMartescitas1(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 4\n" +
            "\t\t\tand paciente_idpaciente =?1")
    List<Cita> listaMiercolescitas1(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 5\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaJuevescitas1(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 6\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaViernescitas1(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 1  AND DAYOFWEEK(fecha) = 7\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaSabadoscitas1(Integer id);

    //-------------a 2 semanas------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 2\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaLunescita2(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 3\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaMartescitas2(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 4\n" +
            "\t\t\tand paciente_idpaciente =?1")
    List<Cita> listaMiercolescitas2(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 5\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaJuevescitas2(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 6\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaViernescitas2(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 2  AND DAYOFWEEK(fecha) = 7\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaSabadoscitas2(Integer id);

    //-------------A 3 semanas------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 2\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaLunescitas3(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 3\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaMartescitas3(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 4\n" +
            "\t\t\tand paciente_idpaciente =?1")
    List<Cita> listaMiercolescitas3(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 5\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaJuevescitas3(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 6\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaViernescitas3(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 3  AND DAYOFWEEK(fecha) = 7\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaSabadoscitas3(Integer id);

    //-------------A 4 semanas------------------------//
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4  AND DAYOFWEEK(fecha) = 2\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaLunescitas4(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4  AND DAYOFWEEK(fecha) = 3\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaMartescitas4(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4  AND DAYOFWEEK(fecha) = 4\n" +
            "\t\t\tand paciente_idpaciente =?1")
    List<Cita> listaMiercolescitas4(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4  AND DAYOFWEEK(fecha) = 5\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaJuevescitas4(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4  AND DAYOFWEEK(fecha) = 6\n" +
            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaViernescitas4(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM cita WHERE YEARWEEK(fecha) = YEARWEEK(CURDATE()) + 4  AND DAYOFWEEK(fecha) = 7\n" +

            "\t\t\tand paciente_idpaciente = ?1")
    List<Cita> listaSabadoscitas4(Integer id);

    @Query(nativeQuery = true, value = "SELECT DATE_ADD(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + (-2 + ?1) WEEK, INTERVAL (WEEKDAY(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + ?1 WEEK) + 1) DAY) AS start_date;")
    java.sql.Date obtnerInicioSemanacita(Integer numSemana);

    @Query(nativeQuery = true, value = "SELECT DATE_ADD(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + (-2+ ?1) WEEK, INTERVAL (WEEKDAY(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + ?1 WEEK) + 7) DAY) AS end_date;")
    java.sql.Date obtenerFinSemanacita(Integer numSemana);

    @Query(nativeQuery = true, value = "SELECT MONTHNAME(MAKEDATE(YEAR(CURDATE()), 1) + INTERVAL WEEK(CURDATE()) + ?1 WEEK) AS month_name;")
    String obtenerMescita(Integer numWeek);


    @Query(value = "SELECT * from cita where paciente_idpaciente = ?1", nativeQuery = true)
    List<Cita> citasxUsuario(int idusuario);




}


