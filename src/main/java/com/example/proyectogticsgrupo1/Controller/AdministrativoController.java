package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.MailCorreo;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.*;
import com.example.proyectogticsgrupo1.Service.EmailService;
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

import java.time.LocalDate;
import java.time.LocalTime;
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
    private EmailService emailService;

    @Autowired
    MailCorreoRepository mailCorreoRepository;

    @Autowired
    private NotificacionesRepository notificacionesRepository;

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
    public String dashDoc(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        List<Doctor> listaDoctorSD = doctorRepository.listarDoctorporSedeyEspecialidadDashboardDoctores(usuarioAdministrativo.getSede().getIdsede(), usuarioAdministrativo.getEspecialidad().getIdespecialidad()); // a futuro cambiar
        model.addAttribute("listaUsuariosDoctores", listaDoctorSD);
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/dashboarddoctores";
    }

    @GetMapping(value = "/dashboardpacientes")
    public String dashPac(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        List<Paciente> listaPacientesSD = pacienteRepository.listarPacienteporSedeyEspecialidadDashboardPacientes(usuarioAdministrativo.getSede().getIdsede(), usuarioAdministrativo.getEspecialidad().getIdespecialidad()); // a futuro cambiar
        model.addAttribute("listaUsuariosPacientes", listaPacientesSD);
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/dashboardpaciente";
    }




    @GetMapping(value = "/formularioreferido")
    public String formRef(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/formularioreferido";
    }

    @GetMapping(value = "/formularioreferido2")
    public String formRef2(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/formularioreferido2";
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
        model.addAttribute("listamensajes", mailCorreoRepository.buscarMensajesEnviadosyRecibidos(usuarioAdministrativo.getIdusuario(),usuarioAdministrativo.getIdusuario()));

        return"administrativo/mensajes";
    }

    @GetMapping(value = "/notificaciones")
    public String notif(Model model) {
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        model.addAttribute("notificaciones",notificacionesRepository.notificacionesPorUsuario(usuarioAdministrativo.getIdusuario()));
        return "administrativo/notificaciones";
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

    @PostMapping(value = "/enviarmensaje")
    public String enviarMensaje(@RequestParam("correo") String correo,
                                @RequestParam("asunto") String asunto,
                                @RequestParam("descripcion") String descripcion,
                                RedirectAttributes redirectAttributes, Model model) {
        // Verificar si el correo existe en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);

        if (usuario != null) {
            // Crear un nuevo mensaje y asignar los valores
            MailCorreo mensaje = new MailCorreo();
            mensaje.setAsunto(asunto);
            mensaje.setDescripcion(descripcion);
            mensaje.setCorreodestino(correo);
            mensaje.setCorreo(usuario.getCorreo());
            Usuario usuarioo = new Usuario();
            usuarioo.setIdusuario(usuarioAdministrativo.getIdusuario());
            mensaje.setUsuarioOrigen(usuarioo);
            Usuario usuariod = new Usuario();
            usuariod.setIdusuario(usuario.getIdusuario());
            mensaje.setUsuarioDestino(usuariod);
            // Establecer la fecha y hora actual
            mensaje.setFecha(LocalDate.now());
            mensaje.setHora(LocalTime.now());
            mensaje.setPassword("1234");

            // Guardar el mensaje en la base de datos
            mailCorreoRepository.save(mensaje);

            // Lógica para enviar el correo electrónico
            String mensajeCorreo = "Asunto: " + asunto + "\nDescripción: " + descripcion;
            emailService.sendEmail(correo, "Mensaje de Contacto", mensajeCorreo);

            redirectAttributes.addFlashAttribute("mp1", "El correo ha sido enviado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("mp2", "No se puede comunicar con el correo ingresado");
        }

        return "redirect:/administrativo/chat";
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


    @PostMapping(value = "/enviarcorreoadministrativo")
    @Transactional
    public String enviarCorreo(@RequestParam("nombres") String nombres, @RequestParam("dni") String dni,
                               @RequestParam("correo") String correo, RedirectAttributes redirectAttributes, Model model) {
        // Verificar si existe un usuario con el mismo DNI
        Usuario usuarioDni = usuarioRepository.findByDni(dni);

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);

        // Verificar si existe un usuario con el mismo correo electrónico
        Usuario usuarioCorreo = usuarioRepository.findByCorreo(correo);

        if (usuarioDni != null && usuarioCorreo != null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, ya existe un usuario con este DNI y correo electrónico");
        } else if (usuarioDni != null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, ya existe un usuario con este DNI");
        } else if (usuarioCorreo != null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, ya existe un usuario con este correo electrónico");
        } else {
            // Lógica para enviar el correo electrónico
            emailService.sendEmail(correo, "Invitación",
                    "Estimado usuario, usted ha sido invitado a la plataforma de Clínica LA FE:\nIngresa aquí para registrarte: http://localhost:8088/administrativo/formularioreferido2");
            String content = "Usted invito un usuario con CORREO: " + correo ;
            String titulo = "Invitación enviada existosamente";
            notificacionesRepository.notificarCreacion2(usuarioAdministrativo.getIdusuario(),content,titulo);
            redirectAttributes.addFlashAttribute("ms1", "El correo ha sido enviado exitosamente");
        }

        return "redirect:/administrativo/crearpaciente";
    }



}
