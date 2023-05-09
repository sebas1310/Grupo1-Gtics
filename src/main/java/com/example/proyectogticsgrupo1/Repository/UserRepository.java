package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<Usuario, Integer>{
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE usuario SET contrasena = ?1 WHERE idusuario = ?2")
    void changePassword(String newpassword, Integer id);

    @Modifying
    @Query(value= "update usuario set nombres= ?1 ,apellidos= ?2, dni= ?3, correo=?4 where idusuario=?5 ",nativeQuery = true)
    void actualizarPerfilDoctor (String nombres, String apellidos, String dni , String correo, Integer idUsuario);

    @Modifying
    @Transactional
    @Query(value="UPDATE usuario set contrasena = ?1 where idusuario = ?2",nativeQuery = true)
    void cambiarContra(String nuevaContra, Integer id );

    @Query(value= "select * from usuario where idusuario = ?1 ",nativeQuery = true)
    Usuario usuarioDestino (Integer idUsuarioOrigen);
}
