package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Paciente;


import com.example.proyectogticsgrupo1.Entity.Tipodeusuario;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //List<Usuario> findByTipodeusuarioIdtipodeusuario(int id);


    public Usuario findByCorreo(String correo);

    public Usuario findByDni(String dni);

    public Usuario findByCelular(String celular);



    @Query(value= "select * from usuario where correo = ?1 and contrasena = ?2 ",nativeQuery = true)
    Usuario validarLoginDeUsuario (String correo, String contrasena);


    @Query(value = "SELECT * FROM paciente p\n" +
            "    JOIN usuario u ON p.idusuario = u.idusuario\n" +
            "    JOIN estadopaciente e on p.idestadopaciente = e.idestadopaciente WHERE u.idtipodeusuario = ?1", nativeQuery = true)
    List<Paciente> buscarPaciente(int idtipodeusuario);


    @Modifying
    @Transactional
    @Query(value="update usuario set contrasena = ?1 where idusuario = 1",nativeQuery = true)
    void cambiarPassword(String nuevaContraseña);


    @Query(value= "select * from usuario where idusuario = ?1 ",nativeQuery = true)
    Usuario buscarPorId (Integer idusuario);

    @Transactional
    @Modifying
    @Query(value= "update usuario set nombres= ?1 ,apellidos= ?2, correo=?3, celular=?4 where idusuario=?5 ",nativeQuery = true)
    void perfil(String nombres, String apellidos, String correo, String celular, int idusuario);

    @Transactional
    @Modifying
    @Query(value = "update usuario set contrasena = ?1 where idusuario = ?2", nativeQuery = true)
    void actualizarcontrasena(String contrasena, int idusuario);


    @Query(value = "SELECT COUNT(*) > 0 FROM usuarios WHERE correo = :correo", nativeQuery = true)
    boolean existeUsuarioPorCorreo(@Param("correo") String correo);




    @Modifying
    @Query(nativeQuery = true, value = "UPDATE usuario SET contrasena = ?1 WHERE idusuario = ?2")
    void changePassword(String newpassword, Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE usuario SET contrasena = ?1 WHERE idusuario = ?2", nativeQuery = true)
    void cambioPassword(String newpassword, Integer id);




    @Modifying
    @Transactional
    @Query(value="update usuario set estado_habilitado = ?1,nombres=?2,apellidos=?3,correo=?4,dni=?5,fechanacimiento=?6,celular=?7 where idusuario = ?8",nativeQuery = true)
    void actualizarPaciente(int habilitado, String nombres, String apellidos, String correo, String DNI, Date fechadenacimiento, String celular, int idusuario);

    @Modifying
    @Query(value= "update usuario set nombres= ?1 ,apellidos= ?2 where idusuario= ?3 ",nativeQuery = true)
    void actualizarPerfilDoctor (String nombres, String apellidos, Integer idUsuario);

    @Modifying
    @Query(value= "update usuario set celular= ?1  where idusuario= ?2 ",nativeQuery = true)
    void actualizarPerfilPaciente (Integer telefono, Integer idUsuario);


    @Modifying
    @Transactional
    @Query(value="UPDATE usuario set contrasena = ?1 where idusuario = ?2",nativeQuery = true)
    void cambiarContra(String nuevaContra, Integer id );

    @Query(value= "select * from usuario where idusuario = ?1 ",nativeQuery = true)
    Usuario usuarioDestino (Integer idUsuarioOrigen);

    List<Usuario> findAllByTipodeusuario(Tipodeusuario tipodeusuario);

    @Query(value=" select * from usuario where idtipodeusuario = 3 and sede_idsede= ?1 and especialidad_idespecialidad = ?2 ",nativeQuery = true)
    List<Usuario> listaAdministrativos (Integer idsede, Integer idespecialidad);

    @Query(value=" select * from usuario where correo = ?1 ",nativeQuery = true)
    Usuario usuarioDestino (String correo);

    @Query(value="SELECT TIMESTAMPDIFF(YEAR, fechanacimiento, CURDATE()) AS edad\n" +
            "FROM usuario\n" +
            "WHERE idusuario = ?1",nativeQuery = true)
    int edad(Integer idusuario);

}


