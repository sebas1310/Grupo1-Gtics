package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Administrador;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    @Query(value = "select idusuario from administrativo where idsede = ?1", nativeQuery = true)
    List<Administrador> administradorPorSede(int idsede);
}
