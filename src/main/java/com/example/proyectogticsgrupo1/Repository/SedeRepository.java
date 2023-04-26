package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SedeRepository extends JpaRepository<Sede,Integer> {

    @Query(value= "select * from sede where idsede = ?1 ",nativeQuery = true)
    Sede buscarSedePorId (Integer idSede);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor set idsede =?1 WHERE iddoctor =?2", nativeQuery = true)
    void  cambiarSede(int sede_id, int doctor_id);
}



