package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Especialidad;
import com.example.proyectogticsgrupo1.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EspecialidadRepository extends JpaRepository<Especialidad,Integer> {

}
