package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.BitacoraDeDiagnostico;
import com.example.proyectogticsgrupo1.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BitacoraDeDiagnosticoRepository extends JpaRepository<BitacoraDeDiagnostico, Integer> {

    @Query(value= "select * from bitacoradediagnostico where idpaciente = ?1 order by fechayhora desc ",nativeQuery = true)
    List<BitacoraDeDiagnostico> bitacoraDeDiagnostico(Integer idPaciente);

    @Query(value= "select * from bitacoradediagnostico where idbitacoradediagnostico = ?1 ",nativeQuery = true)
    BitacoraDeDiagnostico buscarBitacoraDeDiagnosticoID (Integer idBitacora);

    @Modifying
    @Query(value= "insert into bitacoradediagnostico (descripcion,fechayhora,idpaciente,iddoctor) values (?1,current_timestamp(),?2,?3) ",nativeQuery = true)
    void guardarbitacora (String descripcion , Integer idPaciente,Integer idDoctor);

    @Modifying
    @Query(value= "delete from bitacoradediagnostico where idbitacoradediagnostico= ?1 ",nativeQuery = true)
    void borrarbitacora (Integer idBitacora);
}
