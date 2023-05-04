package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
    @Query(value = "SELECT idusuario from doctor where idsede = ?1", nativeQuery=true)
            List<Doctor> doctoresPorSede(int idsede);


}
