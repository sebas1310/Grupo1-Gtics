package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.Random;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/administrador", method = RequestMethod.GET)

public class AdministradorController {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    TipodeusuarioRepository tipodeusuarioRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("")
    public String administrador(Model model) {
            Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
            List<Paciente> listaPacientesSD = pacienteRepository.listarPacienteporSedeDashboard(usuarioAdministrador.getSede().getIdsede()); // a futuro cambiar
            model.addAttribute("listaUsuariosPacientes", listaPacientesSD);
            List<Doctor> listaDoctoresSD = doctorRepository.listarDoctorporSedeDashboard(usuarioAdministrador.getSede().getIdsede());
            model.addAttribute("listaUsuarioDoctores", listaDoctoresSD);
            model.addAttribute("usuario",usuarioAdministrador);
        return "administrador/dashboard";
    }

    @GetMapping(value = "/nuevopaciente")
    public String creandoPaciente(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/nuevopaciente";

    }


    @GetMapping(value = "/formcreando")
    public String newPaciente() {
        return "administrador/crearpaciente";
    }

    /*@PostMapping(value = "/guardar2")
    public String guardarUsuario(Usuario user, RedirectAttributes attr, Model model, @RequestParam("contrasena2") String contrasena2){

        if (user.getIdusuario() == null) {
            attr.addFlashAttribute("msg", "Paciente creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Paciente actualizado exitosamente");
        }
            Tipodeusuario tipodeusuario = new Tipodeusuario();
            tipodeusuario.setIdtipodeusuario(4);
            user.setEstadohabilitado(1);
            user.setTipodeusuario(tipodeusuario);
            if (user.getContrasena().equals(contrasena2)) {
                usuarioRepository.save(user);
                return "redirect:/administrador/crearpaciente";
            }
            else{
                model.addAttribute("msg", "Contraseñas no iguales");
                return "administrador/crearpaciente";
            }


    }*/

    @PostMapping(value = "/guardar2")
    public String guardarUsuario(Usuario user, RedirectAttributes attr, Model model, @RequestParam("direccion") String direccion){

        if (user.getIdusuario() == null) {
            attr.addFlashAttribute("msg", "Paciente creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Paciente actualizado exitosamente");
        }
        Tipodeusuario tipodeusuario = new Tipodeusuario();
        tipodeusuario.setIdtipodeusuario(4);
        user.setEstadohabilitado(1);
        user.setTipodeusuario(tipodeusuario);
        user.setContrasena(generarContrasena(10));
        usuarioRepository.save(user);
        Paciente paciente = new Paciente();
        EstadoPaciente estadoPaciente = new EstadoPaciente();
        estadoPaciente.setIdestadopaciente(1);
        paciente.setEstadoPaciente(estadoPaciente);
        paciente.setDireccion(direccion);
        paciente.setConsentimientos(0);
        Seguro seguro = new Seguro();
        seguro.setIdseguro(7);
        paciente.setSeguro(seguro);
        paciente.setUsuario(user);
        paciente.setCondicionenfermedad("-");
        pacienteRepository.save(paciente);
        return "redirect:/administrador/crearpaciente";
    }

    public static String generarContrasena(int longitud) {
        StringBuilder sb = new StringBuilder(longitud);
        Random random = new Random();

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    @GetMapping(value = "/calendariogeneral")
    public String genCalendar(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/calendariogeneral";
    }
    @GetMapping(value = "/calendariomarzo")
    public String MarzoCalendar() {

        return "administrador/calendariomarzo";
    }

    @GetMapping(value = "/calendariomayo")
    public String MayoCalendar() {

        return "administrador/calendariomayo";
    }

    @GetMapping(value = "/formatos")
    public String formatos(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/formatos";
    }



    //@GetMapping(value = "/dashboardpaciente")
    //public String dashboardpacient(Model model) {

    //List<Usuario> listaUsuario = usuarioRepository.findByTipodeusuarioIdtipodeusuario(4);
    //model.addAttribute("listaUsuario", listaUsuario);

    //return "administrador/dashboardpaciente";
    //}

    /*@GetMapping(value = "/dashboardpaciente")
    public String dashboardpacient(Model model) {
        List<Paciente> listaPacientes = pacienteRepository.test();
        model.addAttribute("listaPacientes", listaPacientes);
        return "administrador/dashboardpaciente";
    }*/

    @GetMapping(value = "/porregistrar")
    public String porRegistrar(Model model, @RequestParam(name="buscando",required = false) String buscando) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            if(buscando == null) {
                List<Paciente> listaPacientesR = pacienteRepository.listarPacienteInvitado(2); // a futuro cambiar
                model.addAttribute("listaUsuariosPInvitados", listaPacientesR);
            }
            else{
                List<Paciente> listaUsuarios = pacienteRepository.buscadorInvitado(buscando.toLowerCase());
                model.addAttribute("listaUsuariosPInvitados", listaUsuarios);
            }
            return "administrador/porregistrar";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @PostMapping(value = "/actualizar")
    public String actualizarEstadoPacientes() {
        pacienteRepository.actualizarEstado();
        return "redirect:/administrador/dashboardpaciente";
    }

    @PostMapping("/buscarInvitado")
    public String buscadorInvitados(@RequestParam("buscando") String buscando, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("buscando",buscando);
        return "redirect:/administrador/porregistrar";
    }

    @GetMapping(value = "/dashboardpaciente")
    public String listaCitas(Model model, @RequestParam(name="buscando",required = false) String buscando) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            if(buscando == null) {
                List<Paciente> listaPacientesS = pacienteRepository.listarPacienteporSede(usuarioAdministrador.getSede().getIdsede()); // a futuro cambiar
                model.addAttribute("listaUsuariosPacientes", listaPacientesS);
            }else{
                List<Paciente> listaUsuarios = pacienteRepository.buscadorPaciente(buscando.toLowerCase());
                model.addAttribute("listaUsuariosPacientes", listaUsuarios);
            }
            return "administrador/dashboardpaciente";
    }


    @PostMapping("/buscarPaciente")
    public String buscadorPacientess(@RequestParam("buscando") String buscando, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("buscando",buscando);
        return "redirect:/administrador/dashboardpaciente";
    }

    @GetMapping(value = "/dashboarddoctor")
    public String dashboarddoc(Model model, @RequestParam(name="buscando",required = false) String buscando) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            if(buscando == null) {
                List<Doctor> listaDoctoresS = doctorRepository.listarDoctorporSede(1);
                model.addAttribute("listaUsuarioDoctores", listaDoctoresS);
            }else{
                List<Doctor> listaUsuarios = doctorRepository.buscadorDoctor(buscando.toLowerCase());
                model.addAttribute("listaUsuarioDoctores", listaUsuarios);
            }
            return "administrador/dashboarddoctor";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @PostMapping("/buscarDoctor")
    public String buscadorDoctor(@RequestParam("buscando") String buscando, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("buscando",buscando);
        return "redirect:/administrador/dashboarddoctor";
    }

    @GetMapping(value = "/configuraciones")
    public String config(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            Sede sede = sedeRepository.findByIdsede(usuarioAdministrador.getSede().getIdsede());
            model.addAttribute("sede",sede);
            return "administrador/configuraciones";

    }

    @GetMapping(value = "/dashboardfinanzas")
    public String dashboardfinanz(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/dashboardfinanzas";

    }

    @GetMapping("/historialclinico")
    public String historialClinico(Model model, @RequestParam("id") int idPaciente){
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            Paciente paciente = pacienteRepository.buscarPacientH(idPaciente);
            model.addAttribute("paciente", paciente);
            List<Cita> citasFuturas = citaRepository.findByPacienteAndFechaAfterOrderByFechaAsc(paciente, LocalDate.now());
            model.addAttribute("citas", citasFuturas);
            return "administrador/historialclinico";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/crearpaciente";

    }

    @GetMapping(value = "/vistaformato")
    public String vistaForm(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/vistaformato";

    }

    @GetMapping(value = "/notificaciones")
    public String notif(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/notificaciones";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/mensajes";
    }

    @GetMapping(value = "/chat")
    public String chat(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/chat";

    }


    @GetMapping(value = "/detallesdoctor")
    public String Detalles(Model model, @RequestParam("id") int idDoctor) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            Doctor doctor = doctorRepository.buscarDoctorH(idDoctor);
            model.addAttribute("doctor",doctor);
            return "administrador/detallesdoctor";

    }

    @GetMapping(value = "/vistacuestionario")
    public String VerCuestionario(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/vistacuestionario";
    }

    @GetMapping(value = "/creardoctor")
    public String crearDoctor(@ModelAttribute("especialidad") Especialidad especialidad, Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
        return "administrador/creardoctor";

    }


    @PostMapping(value = "/guardar3")
    public String guardarDoctor(Usuario user, RedirectAttributes attr, Model model, @RequestParam("especialidad") int idEspecialidad){

        if (user.getIdusuario() == null) {
            attr.addFlashAttribute("doc", "Doctor creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Doctor actualizado exitosamente");
        }


        Tipodeusuario tipodeusuario = new Tipodeusuario();
        tipodeusuario.setIdtipodeusuario(5);
        user.setEstadohabilitado(1);
        user.setTipodeusuario(tipodeusuario);
        Sede sede = new Sede();
        sede.setIdsede(1);
        user.setSede(sede);
        user.setContrasena(generarContrasena(10));
        usuarioRepository.save(user);
        Doctor doctor = new Doctor();
        doctor.setCmp(0);
        doctor.setFormacion("-");
        doctor.setRne(0);
        doctor.setCapacitaciones("-");
        doctor.setSede(sede);
        Especialidad especialidad = new Especialidad();
        especialidad.setIdespecialidad(idEspecialidad);
        doctor.setEspecialidad(especialidad);
        doctor.setUsuario(user);
        doctor.setConsultorio("-");
        doctorRepository.save(doctor);
        return "redirect:/administrador/dashboarddoctor";
    }

    @GetMapping(value = "/perfil")
    public String perfilPaciente(Model model){
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario",usuarioAdministrador);
        return "administrador/perfil";
    }

    @PostMapping(value = "/editarperfil")
    public String editarPerfil(RedirectAttributes redirectAttributes,
                               @RequestParam("id") int idusuario,
                               @RequestParam("nombres") String nombres,
                               @RequestParam("apellidos") String apellidos,
                               @RequestParam("correo") String correo,
                               @RequestParam("celular") String celular){
        usuarioRepository.perfil(nombres,apellidos,correo,celular,idusuario);
        redirectAttributes.addAttribute("id",idusuario);
        return "redirect:/administrador/perfil";
    }

    /*@PostMapping(value = "/contrasena")
    public String actualizarContra(RedirectAttributes redirectAttributes,
                               @RequestParam("id") int idusuario,
                               @RequestParam("contrasena") String contrasena){
        usuarioRepository.actualizarcontrasena(contrasena,idusuario);
        redirectAttributes.addAttribute("id",idusuario);
        return "redirect:/administrador/perfil";
    }*/

    @GetMapping(value = "/nuevo")
    public String nuevoPaciente(Model model){
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
            return "administrador/crearpaciente";
    }

    /*@PostMapping(value = "/guardar")
    public String guardarPaciente(@ModelAttribute("usuario") Usuario usuario){
        Optional<Tipodeusuario> tipodeusuarioopt = tipodeusuarioRepository.findById(4);
        Tipodeusuario tipodeusuario = tipodeusuarioopt.get();
        usuario.setTipodeusuario(tipodeusuario);
        usuario.setEstado(1);
        usuarioRepository.save(usuario);
        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        pacienteRepository.save(paciente);
        return "redirect:/administrador/dashboarddoctor";
    }*/

    @PostMapping(value = "/changecontrasena")
    @Transactional
    public String changePassword(@RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(2);
        Usuario usuario =  usuarioOptional.get();
        if(usuario.getContrasena().equals(contrasena)){
            usuarioRepository.changePassword(renewpassword,usuario.getIdusuario());
            redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada");

        }else {
            redirectAttributes.addFlashAttribute("psw2", "La contraseña es incorrecta");
        }
        return "redirect:/administrador/perfil";
    }
}
