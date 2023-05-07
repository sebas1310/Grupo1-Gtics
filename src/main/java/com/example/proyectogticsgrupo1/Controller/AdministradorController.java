package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Random;

import javax.print.Doc;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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


    @GetMapping("")
    public String administrador(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/dashboard";
        } else {
            return "redirect:/iniciosesion";
        }
    }


    @GetMapping(value = "/nuevopaciente")
    public String creandoPaciente(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/nuevopaciente";
        } else {
            return "redirect:/iniciosesion";
        }
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
    public String guardarUsuario(Usuario user, RedirectAttributes attr, Model model){

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
        estadoPaciente.setIdestadopaciente(2);
        paciente.setEstadoPaciente(estadoPaciente);
        paciente.setDireccion("-");
        paciente.setConsentimientos(0);
        Seguro seguro = new Seguro();
        seguro.setIdseguro(7);
        paciente.setSeguro(seguro);
        paciente.setIdpaciente(10); //cambiar
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
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/calendariogeneral";
        } else {
            return "redirect:/iniciosesion";
        }
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

        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/formatos";
        } else {
            return "redirect:/iniciosesion";
        }
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

    @GetMapping(value = "/dashboardpaciente")
    public String listaCitas(Model model,@RequestParam("id") int idUsuario) {
         Usuario usuario = usuarioRepository.buscarPorId(idUsuario);
         model.addAttribute("usuario",usuario);
         Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            List<Paciente> listaPacientesS = pacienteRepository.listarPacienteporSede(1); // a futuro cambiar
            model.addAttribute("listaUsuariosPacientes", listaPacientesS);
            return "administrador/dashboardpaciente";
        } else {
            return "redirect:/iniciosesion";
        }

    }

    @GetMapping(value = "/dashboarddoctor")
    public String dashboarddoc(Model model,@RequestParam("id") int idUsuario) {
        Usuario usuario = usuarioRepository.buscarPorId(idUsuario);
        model.addAttribute("usuario",usuario);
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            List<Doctor> listaDoctoresS = doctorRepository.listarDoctorporSede(1);
            model.addAttribute("listaUsuarioDoctores", listaDoctoresS);
            return "administrador/dashboarddoctor";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/configuraciones")
    public String config(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            Sede sede = sedeRepository.findByIdsede(1);
            model.addAttribute("sede",sede);
            return "administrador/configuraciones";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/dashboardfinanzas")
    public String dashboardfinanz(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/dashboardfinanzas";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping("/historialclinico")
    public String historialClinico(Model model, @RequestParam("id") int idPaciente){
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            Paciente paciente = pacienteRepository.buscarPacientH(idPaciente);
            model.addAttribute("paciente", paciente);
            List<Cita> citasFuturas = citaRepository.findByPacienteAndFechaAfterOrderByFechaAsc(paciente, LocalDate.now());
            model.addAttribute("citas", citasFuturas);
            return "administrador/historialclinico";
        }else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/crearpaciente";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/invitar")
    public String invitar(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/invitar";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/vistaformato")
    public String vistaForm(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/vistaformato";
        } else {
            return "redirect:/iniciosesion";
        }

    }

    @GetMapping(value = "/notificaciones")
    public String notif(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/notificaciones";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/mensajes")
    public String mensajes(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/mensajes";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/chat")
    public String chat(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/chat";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/detallesdoctor")
    public String Detalles(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/detallesdoctor";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/vistacuestionario")
    public String VerCuestionario(Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/vistacuestionario";
        } else {
            return "redirect:/iniciosesion";
        }
    }

    @GetMapping(value = "/creardoctor")
    public String crearDoctor(@ModelAttribute("especialidad") Especialidad especialidad, Model model) {
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
            return "administrador/creardoctor";
        } else {
            return "redirect:/iniciosesion";
        }

    }


    @PostMapping(value = "/guardar3")
    public String guardarDoctor(Usuario user, RedirectAttributes attr, Model model){

        if (user.getIdusuario() == null) {
            attr.addFlashAttribute("msg", "Doctor creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Doctor actualizado exitosamente");
        }
        Tipodeusuario tipodeusuario = new Tipodeusuario();
        tipodeusuario.setIdtipodeusuario(5);
        user.setEstadohabilitado(1);
        user.setTipodeusuario(tipodeusuario);
        user.setContrasena(generarContrasena(10));
        usuarioRepository.save(user);
        return "redirect:/administrador/crearpaciente";


    }

    @GetMapping(value = "/perfil")
    public String perfilAdministrador(Model model, @RequestParam("id") int idusuario) {
        Usuario usuario = usuarioRepository.buscarPorId(idusuario);
        model.addAttribute("usuario",usuario);
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

    @PostMapping(value = "/contrasena")
    public String actualizarContra(RedirectAttributes redirectAttributes,
                               @RequestParam("id") int idusuario,
                               @RequestParam("contrasena") String contrasena){
        usuarioRepository.actualizarcontrasena(contrasena,idusuario);
        redirectAttributes.addAttribute("id",idusuario);
        return "redirect:/administrador/perfil";
    }
    @GetMapping(value = "/nuevo")
    public String nuevoPaciente(Model model){
        Optional<Usuario> usuarioopt = usuarioRepository.findById(2);
        if (usuarioopt.isPresent()) {
            Usuario user = usuarioopt.get();
            model.addAttribute("usuario", user);
            return "administrador/crearpaciente";
        } else {
            return "redirect:/iniciosesion";
        }
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














}


