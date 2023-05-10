package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.MailCorreo;
import com.example.proyectogticsgrupo1.Entity.Recetamedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MailCorreoRepository extends JpaRepository <MailCorreo,Integer>{

    @Query(value= "select * from mensaje where idusuariodestino = ?1 ",nativeQuery = true)
    List<MailCorreo> buscarMensajesRecibidosPorID (Integer idUsuarioDestino);

    @Query(value= "select * from mensaje where idusuariodestino = ?1 or idusuarioorigen = ?1",nativeQuery = true)
    List<MailCorreo> buscarMensajesPorID (Integer idUsuario);

    @Query(value= "select * from mensaje where idmensaje = ?1 ",nativeQuery = true)
    MailCorreo buscarMensajePorID (Integer idMensaje);

    @Query(value= "select * from mensaje where asunto = ?1 ",nativeQuery = true)
    List<MailCorreo> buscarMensajePorAsunto (String asunto);

    @Modifying
    @Query(value= "insert into mensaje (asunto,descripcion,correodestino,idusuariodestino,idusuarioorigen,fecha,hora,correoorigen,password) values (?1,?2,?3,?4,?5,current_date,current_time,'clinica.lafe.info@gmail.com','tioqhejyxcpyergd') ",nativeQuery = true)
    void guardarMensaje (String asunto,String descripcion ,String correodestino , Integer idUsuarioDestino,Integer idUsuarioOrigen);
}
