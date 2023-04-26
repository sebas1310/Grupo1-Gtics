package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica,Integer> {

    @Query(value= "select * from recetamedica where idcita = ?1 ",nativeQuery = true)
    List<RecetaMedica> buscarRecetaMedicaPorCita (Integer idCita);
    @Modifying
    @Query(value= "insert into recetamedica (medicamento,dosis,descripcion,idcita) values (?1,?2,?3,?4) ",nativeQuery = true)
    void agregarReceta (String medicamento,String dosis ,String descripcion , Integer idCita);

    @Modifying
    @Query(value= "update rectamedica set medicamento = ?1 , dosis = ?2, descripcion = ?3 where idcita = ?4",nativeQuery = true)
    void actualizarReceta (String medicamento, String dosis, String descripcion, Integer idCita);
}
