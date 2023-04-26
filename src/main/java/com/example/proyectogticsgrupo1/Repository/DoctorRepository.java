package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Doctor;
<<<<<<< HEAD
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

    @Query(value = "SELECT d FROM Doctor d JOIN d.usuario u WHERE u.tipodeusuario.idtipodeusuario = 5")
    List<Doctor> listado();
}
=======
import com.example.proyectogticsgrupo1.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

    @Query(value= "select * from doctor where iddoctor = ?1 ",nativeQuery = true)
    Doctor buscarDoctorPorId (Integer idDoc);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor set idsede =?1 WHERE iddoctor =?2", nativeQuery = true)
    void  cambiarSede(int sede_id, int doctor_id);

}
>>>>>>> doctor
