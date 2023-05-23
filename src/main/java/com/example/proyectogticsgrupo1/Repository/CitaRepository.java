package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
<<<<<<< HEAD
import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

>>>>>>> origin
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

<<<<<<< HEAD
    @Query(value = "SELECT p.idpaciente, c.paciente_idpaciente, c.fecha\n" +
        "FROM paciente p\n" +
        "         JOIN cita c ON p.idpaciente= c.paciente_idpaciente", nativeQuery = true)
    List<Cita> citasporpaciente(String citas);

    List<Cita> findByPacienteAndFechaAfterOrderByFechaAsc(Paciente paciente, LocalDate fechaactual);
=======
    @Query(value= "select * from cita where iddoctor= ?1 ",nativeQuery = true)
    List<Cita> pacientesAtendidosPorDoctor(Integer idDoctor);

    @Query(value= "select * from cita where idpaciente= ?1 ",nativeQuery = true)
    List<Cita> citasPorPaciente(Integer idPaciente);

    @Query(value= "select * from cita where idcita = ?1 ",nativeQuery = true)
    Cita buscarCitaPorId (Integer idCita);

    @Query(value= "select * from cita where iddoctor= ?1 and fecha >=current_date() ",nativeQuery = true)
    List<Cita> buscarCitasAgendadasDoctor (Integer idDoctor);
>>>>>>> origin




<<<<<<< HEAD
}
=======
}
>>>>>>> origin
