package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //List<Usuario> findByTipodeusuarioIdtipodeusuario(int id);


    @Transactional
    @Modifying
    @Query(value = "insert into `bdclinica_lafe`.`usuario` (`idusuario`, `idtipodeusuario`, `nombres`, `apellidos`, `dni`, `correo`, `contrasena`, `genero`, `foto`, `celular`, `edad`, `sede_idsede`, `especialidad_idespecialidad`, `estado_habilitado`, `sueldo`, `firma`) values ( ?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16) ",
            nativeQuery = true)
    void guardarUsuario(int idusuario, int idtipodeusuario, String idestadopaciente, int idseguro, String alergias, int consentimientos, String condicion_enfermedad, String poliza, Boolean referido);
    @Query(value = "SELECT * FROM paciente p\n" +
            "    JOIN usuario u ON p.idusuario = u.idusuario\n" +
            "    JOIN estadopaciente e on p.idestadopaciente = e.idestadopaciente WHERE u.idtipodeusuario = ?1", nativeQuery = true)
    List<Paciente> buscarPaciente(int idtipodeusuario);


    @Query(value= "select * from usuario where idusuario = ?1 ",nativeQuery = true)
    Usuario buscarPorId (Integer idusuario);

    @Transactional
    @Modifying
    @Query(value= "update usuario set nombres= ?1 ,apellidos= ?2, correo=?3, celular=?4 where idusuario=?5 ",nativeQuery = true)
    void perfil(String nombres, String apellidos, String correo, String celular,int idusuario);

    @Transactional
    @Modifying
    @Query(value = "update usuario set contrasena = ?1 where idusuario = ?2", nativeQuery = true)
    void actualizarcontrasena(String contrasena, int idusuario);




}
