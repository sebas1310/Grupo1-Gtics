/*package com.example.proyectogticsgrupo1.Repository;*/

import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {


    @Transactional
    @Modifying
    @Query(value = "insert into `bdclinica_lafe`.`paciente` (`idpaciente`, `direccion`, `idestadopaciente`, `idseguro`, `alergias`,`consentimientos`, `condicion_enfermedad`, `poliza`,`referido` ) values ( ?1,?2,?3,?4,?5,?6,?7,?8,?9) ",
            nativeQuery = true)
    void guardarPaciente(int idpaciente, String direccion, int idestadopaciente, int idseguro, String alergias, int consentimientos, String condicion_enfermedad, String poliza, Boolean referido);
    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.tipodeusuario.idtipodeusuario = 4")
    List<Paciente> test();

    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE u.nombres = ?", nativeQuery = true)
    List<Paciente> buscarPaciente();

    @Query(value = "SELECT p FROM Paciente p JOIN p.usuario u WHERE referido = 1", nativeQuery = true)
    List<Paciente> buscarPacienteReferido();

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where c.idsede = ?1", nativeQuery = true)
    List<Paciente> listarPacienteporSede(int idsede);

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where c.idsede = ?1 LIMIT 5", nativeQuery = true)
    List<Paciente> listarPacienteporSedeDashboard(int idsede);

    @Query(value = "select * from paciente where idpaciente= ?1", nativeQuery = true)
    Paciente buscarPacientH(Integer idPaciente);

    @Query(value = "SELECT DISTINCT p.* FROM cita c\n" +
            "       INNER JOIN paciente p ON c.paciente_idpaciente = p.idpaciente\n" +
            "       INNER JOIN estadopaciente e ON p.idestadopaciente = e.idestadopaciente and e.nombre = 'Invitado'\n"+
            "       INNER JOIN usuario u on p.idusuario = u.idusuario where c.idsede = ?1", nativeQuery = true)
    List<Paciente> listarPacienteInvitado(int idsede);

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


}