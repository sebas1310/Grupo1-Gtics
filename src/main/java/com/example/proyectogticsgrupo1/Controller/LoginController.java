package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.SedeRepository;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import com.example.proyectogticsgrupo1.Service.EmailService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EmailService emailService;


    public LoginController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    SedeRepository sedeRepository;

    @GetMapping(value = {"/"})
    public String inicioSesion(){

        return "superadmin/pages-login_spa";
    }
//    superadmin/pages-login_spa

    @PostMapping(value = "/registro")
    public String registro(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult,
                           RedirectAttributes attr, Model model){
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("msg", "presenta errores");
            model.addAttribute("listasedes", sedeRepository.findAll());

            attr.addFlashAttribute("usuario", usuario);
            return "superadmin/formularioregistro_spa";

        }else{

            Usuario existingUserDni = usuarioRepository.findByDni(usuario.getDni());
            Usuario existingUserCelular = usuarioRepository.findByCelular(usuario.getCelular());
            Usuario existingUserCorreo = usuarioRepository.findByCorreo(usuario.getCorreo());

            if(existingUserDni == null){
                if(existingUserCelular == null){
                    if(existingUserCorreo==null){
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String hashedNewPassword = passwordEncoder.encode(usuario.getContrasena());
                        usuario.setContrasena(hashedNewPassword);
                        usuarioRepository.save(usuario);
                        return "redirect:/";

                    }else{
                        model.addAttribute("listasedes", sedeRepository.findAll());
                        bindingResult.rejectValue("correo", "error.correo", "Ya existe un usuario con este correo electrónico");
                        return "superadmin/formularioregistro_spa";
                    }
                }else{
                    model.addAttribute("listasedes", sedeRepository.findAll());
                    bindingResult.rejectValue("celular", "error.celular", "Ya existe un usuario con este número de celular");
                    return "superadmin/formularioregistro_spa";
                }
            }else{
                model.addAttribute("listasedes", sedeRepository.findAll());
                bindingResult.rejectValue("dni", "error.dni", "Ya existe un usuario con este DNI");
                return "superadmin/formularioregistro_spa";
            }
        }


    }

    @GetMapping(value = "/cambiarcontrasena")
    public String cambiarContra(){

        return "cambiarcontrasena";
    }

    @GetMapping(value = "/error")
    public String error(){

        return "error";
    }

    @GetMapping(value = "/cambiarcontrasena2")
    public String cambiarContra2(){

        return "cambiarcontrasena2";
    }

    @GetMapping(value = "/olvidocontra")
    public String olvidoContra(){

        return "/superadmin/olvidocontra_spa";
    }

    @GetMapping(value = "/formRegistro")
    public String formRegistro(@ModelAttribute("usuario") Usuario usuario, Model model){
        model.addAttribute("listasedes", sedeRepository.findAll());

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

    @PostMapping(value = "/changepassword2")
    @Transactional
    public String changePassword2(@RequestParam("correo") String email, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes) {

        // Obtener el usuario por su correo electrónico desde la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(email);

        if (usuario != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedNewPassword = passwordEncoder.encode(newpassword);

                // Actualizar la contraseña en la base de datos
                usuarioRepository.changePassword(hashedNewPassword, usuario.getIdusuario());
                redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada, prueba iniciando sesión");

        } else {
            redirectAttributes.addFlashAttribute("psw3", "No se pudo actualizar, no se encontró ningún usuario con el correo electrónico proporcionado");
        }

        return "redirect:/cambiarcontrasena2";
    }

    @PostMapping(value = "/enviarcorreo")
    public String enviarCorreo(@RequestParam("dni") String dni, @RequestParam("correo") String correo, RedirectAttributes redirectAttributes) {
        // Verificar si existe un correo en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario != null && usuario.getDni().equals(dni)) {
            // Lógica para enviar el correo electrónico
            emailService.sendEmail(correo, "Cambio de Contraseña","Estimado usuario, usted ha solicitado un cambio de contraseña:\nIngresa aquí para cambiarla: http://localhost:8081/cambiarcontrasena2");
            redirectAttributes.addFlashAttribute("ms1", "El correo ha sido enviado exitosamente");
        } else if (usuario == null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, no se encontró ningún usuario con el correo electrónico proporcionado");
        } else {
            redirectAttributes.addFlashAttribute("ms3", "El correo y DNI no coinciden");
        }

        return "redirect:/olvidocontra";
    }




}
