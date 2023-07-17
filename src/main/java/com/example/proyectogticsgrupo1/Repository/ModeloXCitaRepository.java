package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.ModeloXCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloXCitaRepository extends JpaRepository<ModeloXCita,Integer> {

    @Query(value = "select * from modelo_x_cita where idcita_fk=?1 and mostrar_automatico=1 and fill <> 1",nativeQuery = true)
    ModeloXCita porllenar(Integer idcita);

    @Modifying
    @Query(value = "UPDATE modelo_x_cita SET fill = 1 WHERE (id = ?1)", nativeQuery = true)
    void fillcuest (Integer id);

    @Query(value = "select id from modelo_x_cita WHERE idcita_fk = ?1", nativeQuery = true)
    Integer idModelxCita (Integer idCita);

    @Modifying
    @Query(value = "delete from modelo_x_cita where id=?1", nativeQuery = true)
    void borrarModelxCita (Integer idModel);
}
