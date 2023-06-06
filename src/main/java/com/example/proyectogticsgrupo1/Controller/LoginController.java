package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(value = "/registro")
    public String registro(){

        return "superadmin/pages-register_spa";
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
