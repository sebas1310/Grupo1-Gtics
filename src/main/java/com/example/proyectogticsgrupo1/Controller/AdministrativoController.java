package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.DoctorRepository;
import com.example.proyectogticsgrupo1.Repository.PacienteRepository;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/administrativo")
public class AdministrativoController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/dashboard")
    public String administrador(Model model) {
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        List<Paciente> listaPacientesSD = pacienteRepository.listarPacienteporSedeyEspecialidadDashboard(usuarioAdministrativo.getSede().getIdsede(), usuarioAdministrativo.getEspecialidad().getIdespecialidad()); // a futuro cambiar
        model.addAttribute("listaUsuariosPacientes", listaPacientesSD);
        List<Doctor> listaDoctoresSD = doctorRepository.listarDoctorporSedeyEspecialidadDashboard(usuarioAdministrativo.getSede().getIdsede(),usuarioAdministrativo.getEspecialidad().getIdespecialidad());
        model.addAttribute("listaUsuarioDoctores", listaDoctoresSD);
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/dashboard";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/crearpaciente";
    }

    @GetMapping(value = "/dashboarddoctores")
    public String dashDoc(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/dashboarddoctores";
    }

    @GetMapping(value = "/dashboardpacientes")
    public String dashPac(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/dashboardpaciente";
    }

    @GetMapping(value = "/formularioreferido")
    public String formRef(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/formularioreferido";
    }

    @GetMapping(value = "/invitar")
    public String invitar(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "invitarpaciente";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/mensajes";
    }

    @GetMapping(value = "/notificaciones")
    public String notificaciones(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/notificaciones";
    }

    @GetMapping(value = "/nuevopaciente")
    public String nuevoPaciente(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/nuevopaciente";
    }
    @GetMapping(value = "/chat")
    public String chat(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/chat";
    }

    @PostMapping(value = "/changepassword")
    @Transactional
    public String changePassword(@RequestParam("id") int idusuario,@RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes) {

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if (passwordEncoder.matches(contrasena, usuarioAdministrativo.getContrasena())) {
            String hashedNewPassword = passwordEncoder.encode(newpassword);


            //usuarioRepository.changePassword(renewpassword, usuarioAdministrador.getIdusuario());
            usuarioRepository.changePassword(hashedNewPassword, usuarioAdministrativo.getIdusuario());
            redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada");

        } else {
            redirectAttributes.addFlashAttribute("psw2", "La contraseña es incorrecta");
        }

        return "redirect:/administrativo/perfil";
    }

    @GetMapping(value = "/detallesdoctor")
    public String detallesDoctor(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/detallesdoctor";
    }

    @GetMapping(value = "/perfil")
    public String perfil(Model model){

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/perfil";
    }

    @PostMapping(value = "/editarperfil")
    public String editarPerfil(RedirectAttributes redirectAttributes,
                               @RequestParam("id") int idusuario,
                               @RequestParam("nombres") String nombres,
                               @RequestParam("apellidos") String apellidos,
                               @RequestParam("correo") String correo,
                               @RequestParam("celular") String celular) {

        // Validar que el celular tenga exactamente 9 dígitos y sean números
        if (celular.length() != 9 || !celular.matches("\\d{9}")) {
            redirectAttributes.addAttribute("id", idusuario);
            redirectAttributes.addFlashAttribute("errorCelular", "El celular debe tener 9 dígitos numéricos.");
            return "redirect:/administrativo/perfil";
        }

        usuarioRepository.perfil(nombres, apellidos, correo, celular, idusuario);
        session.removeAttribute("usuario");
        session.setAttribute("usuario", usuarioRepository.findById(idusuario).get());
        redirectAttributes.addAttribute("id", idusuario);
        redirectAttributes.addFlashAttribute("msg", "Perfil Actualizado");
        return "redirect:/administrativo/perfil";
    }

    @GetMapping(value = "/configuracion")
    public String conf(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/configuraciones";
    }

}
