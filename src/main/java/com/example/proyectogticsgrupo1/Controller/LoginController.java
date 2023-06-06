package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping(value = "/cambiarcontrasena")
    public String olvidoContra(){

        return "cambiarcontrasena";
    }

    @GetMapping(value = "/formRegistro")
    public String formRegistro(){

        return "superadmin/formularioregistro_spa";
    }

    @PostMapping(value = "/changepassword")
    @Transactional
    public String changePassword(@RequestParam("correo") String email, @RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes) {

        // Obtener el usuario por su correo electrónico desde la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(email);

        if (usuario != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                String hashedNewPassword = passwordEncoder.encode(newpassword);

                // Actualizar la contraseña en la base de datos
                usuarioRepository.changePassword(hashedNewPassword, usuario.getIdusuario());
                redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada, prueba iniciando sesión");
            } else {
                redirectAttributes.addFlashAttribute("psw2", "No se pudo actualizar, la contraseña actual es incorrecta");
            }
        } else {
            redirectAttributes.addFlashAttribute("psw3", "No se pudo actualizar, no se encontró ningún usuario con el correo electrónico proporcionado");
        }

        return "redirect:/cambiarcontrasena";
    }

}
