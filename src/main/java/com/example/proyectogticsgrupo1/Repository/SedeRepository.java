package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SedeRepository extends JpaRepository<Sede, Integer> {

    Sede findByIdsede(int idsede);


    @Query(value= "select * from sede where idsede = ?1 ",nativeQuery = true)
    Sede buscarSedePorId (Integer idSede);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor set idsede =?1 WHERE iddoctor =?2", nativeQuery = true)
    void  cambiarSede(int sede_id, int doctor_id);

    @Query(value = "SELECT * FROM sede where idsede = ?1",nativeQuery = true)
    Sede todoSede();


    @Query(value = "SELECT * from sede where idsede not in (select usuario.sede_idsede from usuario where usuario.idtipodeusuario = 2)", nativeQuery = true)
    List<Sede> listaSedes();


}
