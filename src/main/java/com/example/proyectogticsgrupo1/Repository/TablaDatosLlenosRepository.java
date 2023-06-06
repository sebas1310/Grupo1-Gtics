package com.example.proyectogticsgrupo1.Repository;


import com.example.proyectogticsgrupo1.Entity.ModeloJson;
import com.example.proyectogticsgrupo1.Entity.TablaDatosLlenos;
import com.example.proyectogticsgrupo1.Entity.TablaTitulosInputs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TablaDatosLlenosRepository extends JpaRepository<TablaDatosLlenos,Integer> {


    @Modifying
    @Transactional
    @Query(value="insert into tabla_datos_llenos(datos_llenos_inputs) values (?1)",nativeQuery = true)
    void agregarDatosDeInput(String nuevoDatoDeInput);


    @Modifying
    @Transactional
    @Query(value="INSERT INTO datos_json (nombre_plantilla,idusuario,modelo_json_id,cita_idcita, datos_llenos)\n" +
            "SELECT ?2 AS nombre_plantilla,?3,?4,?5, JSON_OBJECTAGG(titulos_modelo,datos_llenos_inputs) AS datos_llenos\n" +
            "FROM (\n" +
            "    SELECT titulos_modelo, datos_llenos_inputs\n" +
            "    FROM (\n" +
            "        SELECT row_number() over() as numero_titulo, col_name AS titulos_modelo\n" +
            "        FROM modelo_json\n" +
            "        CROSS JOIN JSON_TABLE(JSON_KEYS(modelo_json.datos), '$[*]'\n" +
            "            COLUMNS (\n" +
            "                col_name VARCHAR(255) PATH '$'\n" +
            "            )\n" +
            "        ) AS keys_2\n" +
            "        WHERE modelo_json.id = ?1 \n" +
            "    ) AS subquery1\n" +
            "    LEFT JOIN (\n" +
            "        SELECT row_number() over() as numero_dato,datos_llenos_inputs\n" +
            "\t\tFROM tabla_datos_llenos\n" +
            "    ) AS tabla_datos_llenos_inputs on tabla_datos_llenos_inputs.numero_dato = subquery1.numero_titulo\n" +
            ") AS temp;",nativeQuery = true)
    void LlenadoDePlantilla(int id_registro_nuevo,String nombre_plantilla,int id_usuario,int id_modelo,int id_cita);


    @Modifying
    @Transactional
    @Query(value="delete from tabla_datos_llenos",nativeQuery = true)
    void BorrarDatosDeInput();


    @Modifying
    @Transactional
    @Query(value="INSERT INTO datos_json (nombre_plantilla,idusuario,modelo_json_id,cita_idcita, datos_llenos)\n" +
            "SELECT ?1 AS nombre_plantilla,?2,?3,?4, JSON_OBJECTAGG(titulos_modelo,datos_llenos_inputs) AS datos_llenos\n" +
            "FROM (\n" +
            "    SELECT titulos_modelo, datos_llenos_inputs\n" +
            "    FROM (\n" +
            "        SELECT row_number() over() as numero_titulo, col_name AS titulos_modelo\n" +
            "        FROM modelo_json\n" +
            "        CROSS JOIN JSON_TABLE(JSON_KEYS(modelo_json.datos), '$[*]'\n" +
            "            COLUMNS (\n" +
            "                col_name VARCHAR(255) PATH '$'\n" +
            "            )\n" +
            "        ) AS keys_2\n" +
            "        WHERE modelo_json.id = ?3 \n" +
            "    ) AS subquery1\n" +
            "    LEFT JOIN (\n" +
            "        SELECT row_number() over() as numero_dato,datos_llenos_inputs\n" +
            "\t\tFROM tabla_datos_llenos\n" +
            "    ) AS tabla_datos_llenos_inputs on tabla_datos_llenos_inputs.numero_dato = subquery1.numero_titulo\n" +
            ") AS temp;",nativeQuery = true)
    void llenadoDeInformeMedico(String nombre_plantilla,int id_usuario,int id_modelo,int id_cita);


}
