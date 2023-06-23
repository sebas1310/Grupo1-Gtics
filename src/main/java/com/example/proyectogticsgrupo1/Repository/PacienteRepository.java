package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {


    @Query(nativeQuery = true, value = "Select * from paciente WHERE idusuario = ?1")
    Paciente pacXuser(Integer id);


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE paciente SET alergias = ?1 WHERE idpaciente = ?2")
    void modificarAlergia(String alergias, Integer idpaciente);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE paciente SET especialidades_pendientes = ?1 WHERE idpaciente = ?2")
    void modificarEspecialidadesPendientes(String especialidadesPendientes, Integer idpaciente);


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE paciente \n" +
            "SET alergias = TRIM(BOTH ',' FROM CONCAT_WS(',', SUBSTRING_INDEX(alergias, ',', ?1 - 1), SUBSTRING_INDEX(alergias, ',', -((LENGTH(alergias) - LENGTH(REPLACE(alergias, ',', '')))) + ?1 - 1)))\n" +
            "WHERE idpaciente = ?2 ")
    void borrarAlergia(int idx, Integer idpaciente);



    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.tipodeusuario.idtipodeusuario = 4")
    List<Paciente> test();


    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.nombres = ?", nativeQuery = true)
    List<Paciente> buscarPaciente();


    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where c.idsede = ?1", nativeQuery = true)
    List<Paciente> listarPacienteporSede(int idsede);

    @Query(value = "SELECT DISTINCT p.* FROM paciente p\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where u.sede_idsede = ?1 LIMIT 5", nativeQuery = true)
    List<Paciente> listadopacientes(int idsede);

    @Query(value = "SELECT DISTINCT p.* FROM paciente p\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where u.sede_idsede = ?1", nativeQuery = true)
    List<Paciente> listadopacientesdashboard(int idsede);

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where c.idsede = ?1 and c.idespecialidad = ?2 LIMIT 5", nativeQuery = true)
    List<Paciente> listarPacienteporSedeyEspecialidadDashboard(int idsede, int idespecialidad);

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where c.idsede = ?1 and c.idespecialidad = ?2", nativeQuery = true)
    List<Paciente> listarPacienteporSedeyEspecialidadDashboardPacientes(int idsede, int idespecialidad);

    @Query(value = "select * from paciente where idpaciente= ?1", nativeQuery = true)
    Paciente buscarPacientH(Integer idPaciente);

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente and e.nombre = 'Invitado'\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where c.idsede = ?1", nativeQuery = true)
    List<Paciente> listarPacienteInvitado(int idsede);

    @Query(value = "SELECT DISTINCT p.* FROM paciente p\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente and e.nombre = 'Invitado'\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where u.sede_idsede = ?1", nativeQuery = true)
    List<Paciente> listarpinvitado(int idsede);

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n " +
            "INNER JOIN usuario u on p.idusuario = u.idusuario\n"+
            "INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n" +
            "WHERE ((lower(u.nombres) like %?1%\n " +
            "OR lower(u.apellidos) like %?1%\n" +
            "OR lower(e.nombre) like %?1%)  and estado_habilitado = 1 and c.idsede = 2)", nativeQuery = true)
    List<Paciente> buscadorPaciente(String buscando);

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n " +
            "INNER JOIN usuario u on p.idusuario = u.idusuario\n"+
            "INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente and e.nombre ='Invitado'\n" +
            "WHERE ((lower(u.nombres) like %?1%\n " +
            "OR lower(u.apellidos) like %?1%\n" +
            "OR lower(e.nombre) like %?1%)  and estado_habilitado = 1 and c.idsede = 2)", nativeQuery = true)
    List<Paciente> buscadorInvitado(String buscando);

    @Transactional
    @Modifying
    @Query(value = "UPDATE paciente SET idestadopaciente = 2 WHERE idestadopaciente = 1\n", nativeQuery = true)
    void actualizarEstado();

    @Query(value = "select * from paciente where idpaciente= ?1", nativeQuery = true)
    Paciente buscarPacientePorID(Integer idPaciente);

    @Transactional
    @Modifying
    @Query(value = "UPDATE paciente SET idestadopaciente = ?1 where idpaciente = ?2", nativeQuery = true)
    void actualizarEstadoPaciente(Integer idestadopaciente, Integer idpaciente);

    @Query(value = "select * from paciente where idusuario= ?1", nativeQuery = true)
    Paciente buscarPacientePorIdUsuario(Integer idUsuario);

    @Modifying
    @Query(value= "update paciente set direccion = ?1 where idusuario = ?2",nativeQuery = true)
    void actualizarPaciente (String direccion, Integer idUsuario);







}
