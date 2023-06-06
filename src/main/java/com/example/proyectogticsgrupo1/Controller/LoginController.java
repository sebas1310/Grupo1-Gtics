package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/registro")
    public String registro(@ModelAttribute("nuevousuario") @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes attr, Model model){

        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("msg", "presenta errores");

            return "superadmin/formularioregistro_spa";

        }else{
            Usuario existingUserDni = usuarioRepository.findByDni(usuario.getDni());
            Usuario existingUserCelular = usuarioRepository.findByCelular(usuario.getCelular());
            Usuario existingUserCorreo = usuarioRepository.findByCorreo(usuario.getCorreo());

            if(existingUserDni == null){
                if(existingUserCelular == null){
                    if(existingUserCorreo==null){
                        usuarioRepository.save(usuario);
                        return "superadmin/pages-login_spa";

                    }else{
                        bindingResult.rejectValue("correo", "error.correo", "Ya existe un usuario con este correo electrónico");
                        return "superadmin/formularioregistro_spa";
                    }
                }else{
                    bindingResult.rejectValue("celular", "error.celular", "Ya existe un usuario con este número de celular");
                    return "superadmin/formularioregistro_spa";
                }
            }else{
                bindingResult.rejectValue("dni", "error.dni", "Ya existe un usuario con este DNI");
                return "superadmin/formularioregistro_spa";
            }
        }


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
