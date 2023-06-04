package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones,Integer> {
    @Modifying
    @Query(value = "INSERT INTO notificaciones (idusuariodestino,contenido,titulo,fecha,hora) " +
            "VALUES (?1,?2,?3,CURRENT_DATE,CURRENT_TIME)",nativeQuery = true)
    void notificarcita(Integer iddestino,String contenido, String titulo);

    @Query(value = "SELECT * FROM  notificaciones WHERE idusuariodestino=?1", nativeQuery = true)
    List<Notificaciones> notificacionesPorUsuario(Integer id);

}
