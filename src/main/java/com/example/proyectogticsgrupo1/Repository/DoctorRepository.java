package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;



import com.example.proyectogticsgrupo1.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

    @Query(value = "SELECT * from doctor where idsede = ?1", nativeQuery=true)
    List<Doctor> doctoresPorEsp(int idsede);


    /*@Query(value="select u.nombres,u.apellidos,e.nombre from usuario u "+
            "join doctor d on d.idusuario = u.idusuario "+
            "join especialidad e on e.idespecialidad=d.idespecialidad where u.idtipodeusuario=5", nativeQuery = true)
    List<Doctor> listado();*/

    /*@Query(value = "SELECT d FROM Doctor d JOIN d.usuario u WHERE u.tipodeusuario.idtipodeusuario = 5")
    List<Doctor> listado();*/

    @Query(value = "SELECT * from doctor where idsede = 2", nativeQuery = true)
    List<Doctor> listarDoctorporSede(int idsede);

    @Query(value = "SELECT * from doctor where idsede = 2 LIMIT 5", nativeQuery = true)
    List<Doctor> listarDoctorporSedeDashboard(int idsede);

    @Query(value = "SELECT DISTINCT d.* FROM doctor d\n" +
            "            INNER JOIN usuario u on d.idusuario = u.idusuario\n" +
            "            INNER JOIN especialidad e on d.idespecialidad = e.idespecialidad\n" +
            "            WHERE ((lower(u.nombres) like %?1%\n" +
            "            OR lower(u.apellidos) like %?1%\n" +
            "            OR lower(e.nombre) like %?1%)and estado_habilitado = 1 and d.idsede = 2)", nativeQuery = true)
    List<Doctor> buscadorDoctor(String buscando);

    @Query(value = "select * from doctor where iddoctor= ?1", nativeQuery = true)
    Doctor buscarDoctorH(Integer idDoctor);

    @Query(value = "SELECT d FROM Doctor d JOIN d.usuario u WHERE u.tipodeusuario.idtipodeusuario = 5")
    List<Doctor> listado();

    @Query(value= "select * from doctor where iddoctor = ?1 ",nativeQuery = true)
    Doctor buscarDoctorPorId (Integer idDoc);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor set idsede =?1 WHERE iddoctor =?2", nativeQuery = true)
    void  cambiarSede(int idsede, int iddoctor);

    @Modifying
    @Transactional
    @Query(value="UPDATE usuario set contrasena = ?1 where idusuario = ?2",nativeQuery = true)
    void cambiarContra(String nuevaContra, Integer id );

}

