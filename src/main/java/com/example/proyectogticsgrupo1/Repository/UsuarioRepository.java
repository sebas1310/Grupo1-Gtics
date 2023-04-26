package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //List<Usuario> findByTipodeusuarioIdtipodeusuario(int id);

    @Query(value = "SELECT * FROM paciente p\n" +
            "    JOIN usuario u ON p.idusuario = u.idusuario\n" +
            "    JOIN estadopaciente e on p.idestadopaciente = e.idestadopaciente WHERE u.idtipodeusuario = ?1", nativeQuery = true)
    List<Paciente> buscarPaciente(int idtipodeusuario);


}