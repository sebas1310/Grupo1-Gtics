package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.DatosJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatosJsonRepository extends JpaRepository<DatosJsonEntity,Integer>{

    @Query(value = "SELECT * from datos_json where idusuario = ?1", nativeQuery = true)
    List<DatosJsonEntity> listarparapaciente(int idpaciente);

}
