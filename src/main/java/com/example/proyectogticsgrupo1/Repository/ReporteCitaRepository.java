package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.ReporteCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReporteCitaRepository extends JpaRepository<ReporteCita,Integer> {

    @Query(value= "select * from reportecita where idcita = ?1 ",nativeQuery = true)
    ReporteCita buscarReporteCitaPorId (Integer idCita);

    @Modifying
    @Query(value= "insert into reportecita (descripcion,fecha,idcita) values (?1,current_date(),?2) ",nativeQuery = true)
    void añadirReporteCita (String descripcion , Integer idCita);

    @Modifying
    @Query(value= "update reportecita set descripcion = ?1 where idcita = ?2",nativeQuery = true)
    void actualizarReporteCita (String descripcion , Integer idCita);


}
