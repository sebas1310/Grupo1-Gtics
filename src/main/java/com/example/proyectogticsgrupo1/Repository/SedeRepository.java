package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

public interface SedeRepository extends JpaRepository<Sede, Integer> {

    @Query(value = "SELECT * FROM sede where idsede = ?1",nativeQuery = true)
    Sede todoSede();

    Sede findByIdsede(int idsede);





}