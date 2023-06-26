package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.DTO.ModeloJsonLlenado;
import com.example.proyectogticsgrupo1.Entity.DatosJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatosJsonRepository extends JpaRepository<DatosJsonEntity,Integer>{

    @Query(value = "SELECT * from datos_json where idusuario = ?1", nativeQuery = true)
    List<DatosJsonEntity> listarparapaciente(int idpaciente);


    @Query(value = "SELECT count(*) from datos_json", nativeQuery = true)
    Integer contarRegistros();


    @Query(value = "SELECT id from datos_json where modelo_json_id = ?1 and cita_idcita=?2",nativeQuery = true)
    Integer idDatosJson (int idModeloJson, int idCita);

    @Query(value = "select * from datos_json where nombre_plantilla like 'INF%' and cita_idcita= ?1",nativeQuery = true)
    List<Integer> listaIDDatosJsonDeInformesMedicos (int idCita);


    @Query(value = "with temp1 as( \n " +
            "SELECT datos_json.*,jt.valores as valores_json\n" +
            "FROM datos_json, JSON_TABLE(datos_json.datos_llenos, '$.*'\n" +
            "        COLUMNS (\n" +
            "            valores VARCHAR(255) PATH '$'\n" +
            "        )\n" +
            "     ) AS jt\n" +
            "WHERE datos_json.id = ?1) \n" +
            ",temp2 as(\n" +
            "SELECT datos_json.*,keys_2.col_name\n" +
            "FROM datos_json,\n" +
            "     JSON_TABLE(JSON_KEYS(datos_json.datos_llenos), '$[*]'\n" +
            "        COLUMNS (\n" +
            "            col_name VARCHAR(255) PATH '$'\n" +
            "        )\n" +
            "     ) AS keys_2\n" +
            "WHERE datos_json.id = ?1 \n" +
            ")\n" +
            ",temp3 as(\n" +
            "select *, row_number() over(partition by id) as preguntas_enumeradas from temp2)\n" +
            ",temp4 as(\n" +
            "select *, row_number() over(partition by id) as respuestas_enumeradas from temp1 )\n" +
            "select temp3.id as 'ID', temp3.nombre_plantilla as 'NombrePlantilla' , temp3.col_name as 'Campo' ,temp4.valores_json as 'Respuesta' ,temp3.modelo_json_id as 'IdModeloJson',temp3.cita_idcita as 'IDCita' ,temp3.idusuario as 'IDUsuario' from temp3 left join temp4 on temp3.preguntas_enumeradas = temp4.respuestas_enumeradas ",nativeQuery = true)
    List<ModeloJsonLlenado> modeloJsonLlenado (Integer idDatosJson);



}
