package com.example.proyectogticsgrupo1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Usuario, Integer>{
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE usuario SET contrasena = ?1 WHERE idusuario = ?2")
    void changePassword(String newpassword, Integer id);

    @Modifying
    @Query(value= "update usuario set nombres= ?1 ,apellidos= ?2, dni= ?3, correo=?4 where idusuario=?5 ",nativeQuery = true)
    void actualizarPerfilDoctor (String nombres, String apellidos, String dni , String correo, Integer idUsuario);
}
