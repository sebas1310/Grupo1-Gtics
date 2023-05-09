package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //List<Usuario> findByTipodeusuarioIdtipodeusuario(int id);

    @Query(value = "SELECT * FROM paciente p\n" +
            "    JOIN usuario u ON p.idusuario = u.idusuario\n" +
            "    JOIN estadopaciente e on p.idestadopaciente = e.idestadopaciente WHERE u.idtipodeusuario = ?1", nativeQuery = true)
    List<Paciente> buscarPaciente(int idtipodeusuario);

    @Modifying
    @Transactional
    @Query(value="update usuario set contrasena = ?1 where idusuario = 1",nativeQuery = true)
    void cambiarPassword(String nuevaContraseña);


    @Modifying
    @Transactional
    @Query(value="update usuario set estado_habilitado = ?1,nombres=?2,apellidos=?3,correo=?4,dni=?5,edad=?6,celular=?7 where idusuario = ?8",nativeQuery = true)
    void actualizarPaciente(int habilitado,String nombres, String apellidos, String correo, String DNI, int edad, String celular, int idusuario);


}

