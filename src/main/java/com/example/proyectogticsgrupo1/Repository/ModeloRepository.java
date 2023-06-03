package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.ModeloEntity;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModeloRepository extends JpaRepository<ModeloEntity,Integer> {


    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo(nombre, preguntas,idtipodeusuario,idespecialidad,nro_inputs)\n" +
            "VALUES (?1,?2,?3,?4,?5)",nativeQuery = true)
    void crearnuevaPlantilla(String nombreplantilla,String mod_datos,int id_rol,int id_especialidad,int nro_inputs);


    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo(nombre, preguntas,idtipodeusuario,idespecialidad,nro_inputs,formulario)\n" +
            "VALUES (?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    void crearnuevaPlantillaForms(String nombreplantilla,String mod_datos,int id_rol,int id_especialidad,int nro_inputs,int flg_formulario);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo(nombre, preguntas,idtipodeusuario,idespecialidad,nro_inputs,informe)\n" +
            "VALUES (?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    void crearnuevaPlantillaInforme(String nombreplantilla,String mod_datos,int id_rol,int id_especialidad,int nro_inputs,int flg_informe);


    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo(nombre, preguntas,idtipodeusuario,idespecialidad,nro_inputs,cuestionario)\n" +
            "VALUES (?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    void crearnuevaPlantillaCuestionario(String nombreplantilla,String mod_datos,int id_rol,int id_especialidad,int nro_inputs,int flg_cuestionario);


    @Modifying
    @Transactional
    @Query(value="UPDATE modelo SET nombre = ?1 WHERE idmodelo = ?2",nativeQuery = true)
    void actualizarPlantilla(String nuevoNbrPlantilla,int id_modelo);







}
