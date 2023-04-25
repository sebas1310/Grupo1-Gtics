package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Eventocalendariodoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EventocalendariodoctorRepository extends JpaRepository<Eventocalendariodoctor,Integer> {
    @Query(nativeQuery = true, value = "select * from eventocalendariodoctor where iddoctor=?1")
    Eventocalendariodoctor calendarioPorDoctor(Integer iddoc);
}
