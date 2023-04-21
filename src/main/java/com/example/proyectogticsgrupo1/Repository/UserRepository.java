package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Usuario, Integer>{
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE usuario SET contrasena = ?1 WHERE idusuario = ?2")
    void changePassword(String newpassword, Integer id);
}
