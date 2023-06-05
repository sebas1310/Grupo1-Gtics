package com.example.proyectogticsgrupo1.Repository;


import com.example.proyectogticsgrupo1.Entity.TablaTitulosInputs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TablaTitulosInputsRepository extends JpaRepository<TablaTitulosInputs,Integer> {


    @Modifying
    @Transactional
    @Query(value="insert into tabla_titulos_inputs(nombre_titulos) values (?1)",nativeQuery = true)
    void agregarNombreTitulos(String nuevo_nombre);


    @Query(value="select count(*) from modelo_json",nativeQuery = true)
    Integer contarRegistros();



    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo_json (nombre_plantilla,idtipodeusuario,idespecialidad,formulario, datos,habilitado)\n" +
            "    SELECT nombre_plantilla,?2,?3,?4, datos_json,1 FROM (SELECT ?1 as nombre_plantilla, JSON_OBJECTAGG(nombre_titulos, '') AS datos_json FROM tabla_titulos_inputs) AS temp",nativeQuery = true)
    void agregarNuevoFormulario(String nombre_plantilla,int id_rol,int id_especialidad,int tipo_plantilla);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo_json (nombre_plantilla,idtipodeusuario,idespecialidad,informe, datos,habilitado)\n" +
            "    SELECT nombre_plantilla,?2,?3,?4, datos_json,1 FROM (SELECT ?1 as nombre_plantilla, JSON_OBJECTAGG(nombre_titulos, '') AS datos_json FROM tabla_titulos_inputs) AS temp",nativeQuery = true)
    void agregarNuevoInforme(String nombre_plantilla,int id_rol,int id_especialidad,int tipo_plantilla);


    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo_json (nombre_plantilla,idtipodeusuario,idespecialidad,cuestionario, datos,habilitado)\n" +
            "    SELECT nombre_plantilla,?2,?3,?4, datos_json,1 FROM (SELECT ?1 as nombre_plantilla, JSON_OBJECTAGG(nombre_titulos, '') AS datos_json FROM tabla_titulos_inputs) AS temp",nativeQuery = true)
    void agregarNuevoCuestionario(String nombre_plantilla,int id_rol,int id_especialidad,int tipo_plantilla);



    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo_json (id,nombre_plantilla,idtipodeusuario,idespecialidad,formulario, datos)\n" +
            "    SELECT ?5,nombre_plantilla,?2,?3,?4, datos_json FROM (SELECT ?1 as nombre_plantilla, JSON_OBJECTAGG(nombre_titulos, '') AS datos_json FROM tabla_titulos_inputs) AS temp",nativeQuery = true)
    void agregarNuevoFormulario_2(String nombre_plantilla,int id_rol,int id_especialidad,int tipo_plantilla,int primerValorInt_id);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo_json (id,nombre_plantilla,idtipodeusuario,idespecialidad,informe, datos)\n" +
            "    SELECT ?5,nombre_plantilla,?2,?3,?4, datos_json FROM (SELECT ?1 as nombre_plantilla, JSON_OBJECTAGG(nombre_titulos, '') AS datos_json FROM tabla_titulos_inputs) AS temp",nativeQuery = true)
    void agregarNuevoInforme_2(String nombre_plantilla,int id_rol,int id_especialidad,int tipo_plantilla,int primerValorInt_id);


    @Modifying
    @Transactional
    @Query(value="INSERT INTO modelo_json (id,nombre_plantilla,idtipodeusuario,idespecialidad,cuestionario, datos)\n" +
            "    SELECT ?5,nombre_plantilla,?2,?3,?4, datos_json FROM (SELECT ?1 as nombre_plantilla, JSON_OBJECTAGG(nombre_titulos, '') AS datos_json FROM tabla_titulos_inputs) AS temp",nativeQuery = true)
    void agregarNuevoCuestionario_2(String nombre_plantilla,int id_rol,int id_especialidad,int tipo_plantilla,int primerValorInt_id);



    @Modifying
    @Transactional
    @Query(value="delete from tabla_titulos_inputs",nativeQuery = true)
    void BorrarTitulosInput();
}
