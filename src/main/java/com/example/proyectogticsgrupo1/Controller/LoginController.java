package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(value = "/")

public class LoginController {

    final UsuarioRepository usuarioRepository;


    public LoginController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    @GetMapping(value = {"/"})
    public String inicioSesion(){

        return "superadmin/pages-login_spa";
    }
//    superadmin/pages-login_spa

    /* @PostMapping(value = {"/validacionusuario"})
    public String validacionDeUsuario(@RequestParam("correo") String correo , @RequestParam("contrasena") String contrasena){
        Optional<Usuario> optionalUsuario = Optional.ofNullable(usuarioRepository.validarLoginDeUsuario(correo, contrasena));

        return "superadmin/pages-login_spa";
    } */

    @GetMapping(value = "/registro")
    public String registro(){

        return "superadmin/pages-register_spa";
    }

    @GetMapping(value = "/olvidocontra")
    public String olvidoContra(){

        return "superadmin/olvidocontra_spa";
    }

    @GetMapping(value = "/formRegistro")
    public String formRegistro(){

        return "superadmin/formularioregistro_spa";
    }

}
