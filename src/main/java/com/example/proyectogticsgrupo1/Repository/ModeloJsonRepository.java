package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.ModeloJson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModeloJsonRepository extends JpaRepository<ModeloJson, Integer> {

    @Query(value = "SELECT * from modelo_json", nativeQuery = true)
    List<ModeloJson> listarNombresP();

}