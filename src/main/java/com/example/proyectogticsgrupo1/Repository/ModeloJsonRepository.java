package com.example.proyectogticsgrupo1.Repository;


import com.example.proyectogticsgrupo1.Entity.ModeloJson;
import com.example.proyectogticsgrupo1.Entity.ModeloJsonEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ModeloJsonRepository extends JpaRepository<ModeloJsonEntity,Integer> {
    @Query(value = "SELECT titulos_modelo\n" +
            "    FROM (\n" +
            "        SELECT row_number() over() as numero_titulo, col_name AS titulos_modelo\n" +
            "        FROM modelo_json\n" +
            "        CROSS JOIN JSON_TABLE(JSON_KEYS(modelo_json.datos), '$[*]'\n" +
            "            COLUMNS (\n" +
            "                col_name VARCHAR(255) PATH '$'\n" +
            "            )\n" +
            "        ) AS keys_2\n" +
            "        WHERE modelo_json.id = ?1\n" +
            "    ) AS subquery1\n" +
            "    LEFT JOIN (\n" +
            "        SELECT row_number() over() as numero_dato,datos_llenos_inputs\n" +
            "\t\tFROM tabla_datos_llenos\n" +
            "    ) AS tabla_datos_llenos_inputs on tabla_datos_llenos_inputs.numero_dato = subquery1.numero_titulo", nativeQuery = true)
    List<String> listarPreguntasxPlantilla(int id_modelo_json);

    @Query(value = "SELECT * from modelo_json", nativeQuery = true)
    List<ModeloJsonEntity> listarNombresP();

    @Query(value="select id from modelo_json where informe=1 and idtipodeusuario=5 and idespecialidad = ?1 ",nativeQuery = true)
    int informeMedicoId(Integer idespecialidad);

    @Query(value="select id from modelo_json where cuestionario=1 and idtipodeusuario=4 and idespecialidad = ?1 ",nativeQuery = true)
    int cuestionarioMedicoId(Integer idespecialidad);

    @Modifying
    @Transactional
    @Query(value="update modelo_json set mostrarautomatico = ?1 where cuestionario=1 and idtipodeusuario=4 and idespecialidad = ?2 ",nativeQuery = true)
    void mostrarCuestionarioAutomatico(int mostrarautomatico,Integer idespecialidad);
}

