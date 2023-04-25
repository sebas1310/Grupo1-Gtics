package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica,Integer> {

    @Query(value= "select * from recetamedica where idcita = ?1 ",nativeQuery = true)
    List<RecetaMedica> buscarRecetaMedicaPorCita (Integer idCita);
}
