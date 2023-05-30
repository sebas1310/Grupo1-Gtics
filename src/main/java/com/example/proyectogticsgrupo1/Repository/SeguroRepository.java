package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Seguro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Integer> {

    @Query(nativeQuery = true, value = "select comisiondoctor from seguro where idseguro =?1 ")
    Double getCosto(Integer id);

    @Query(nativeQuery = true, value = "select coaseguro from seguro where idseguro =?1 ")
    Double getCoaseguro(Integer id);
}

