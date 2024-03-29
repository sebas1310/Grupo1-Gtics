package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Especialidad;
import com.example.proyectogticsgrupo1.Entity.Sede;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EspecialidadRepository extends JpaRepository<Especialidad,Integer> {


    @Query(nativeQuery = true, value = "select costo from especialidad where idespecialidad=?1")
    Double getCosto(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM especialidad  WHERE idespecialidad IN (SELECT idespecialidad FROM doctor WHERE idsede = ?1  ) order by nombre asc")
    List<Especialidad> listaEspxSede (Integer id);

    public Especialidad findByIdespecialidad(Integer id);

}

