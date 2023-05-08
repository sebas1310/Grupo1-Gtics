package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    /*@Query(value="select u.nombres,u.apellidos,e.nombre from usuario u "+
            "join doctor d on d.idusuario = u.idusuario "+
            "join especialidad e on e.idespecialidad=d.idespecialidad where u.idtipodeusuario=5", nativeQuery = true)
    List<Doctor> listado();*/

    /*@Query(value = "SELECT d FROM Doctor d JOIN d.usuario u WHERE u.tipodeusuario.idtipodeusuario = 5")
    List<Doctor> listado();*/

    @Query(value = "SELECT DISTINCT d.* FROM cita c\n" +
            "    JOIN doctor d on c.doctor_iddoctor = d.iddoctor\n" +
            "    JOIN usuario u on d.idusuario = u.idusuario where c.idsede = ?1", nativeQuery = true)
    List<Doctor> listarDoctorporSede(int idsede);

    @Query(value = "SELECT DISTINCT d.* FROM cita c\n" +
            "INNER JOIN doctor d ON c.doctor_iddoctor = d.iddoctor\n" +
            "INNER JOIN usuario u on d.idusuario = u.idusuario\n" +
            "INNER JOIN especialidad e on d.idespecialidad = e.idespecialidad\n" +
            "WHERE ((lower(u.nombres) like %?1%\n" +
            "OR lower(u.apellidos) like %?1%\n" +
            "OR lower(e.nombre) like %?1%) and estado_habilitado = 1 and c.idsede = 2)", nativeQuery = true)
    List<Doctor> buscadorDoctor(String buscando);


}