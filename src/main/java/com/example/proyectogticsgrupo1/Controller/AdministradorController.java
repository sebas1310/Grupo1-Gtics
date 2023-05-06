package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        return "administrador/dashboard";
    }

    @GetMapping(value = "/perfil")
    public String perfilAdministrador(Model model) {
        Usuario usuario = usuarioRepository.findByIdusuario(2);
        model.addAttribute("usuario",usuario);
        return "administrador/perfil";
    }
    @GetMapping(value = "/nuevopaciente")
    public String creandoPaciente(Model model) {
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
    public String genCalendar() {

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
    public String formatos() {

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

    @GetMapping(value = "/dashboardpaciente")
    public String listaCitas(Model model) {

        List<Paciente> listaPacientesS = pacienteRepository.listarPacienteporSede(2); // a futuro cambiar
        model.addAttribute("listaUsuariosPacientes", listaPacientesS);
        return "administrador/dashboardpaciente";
    }

    @GetMapping(value = "/dashboarddoctor")
    public String dashboarddoc(Model model) {
        List<Doctor> listaDoctoresS = doctorRepository.listarDoctorporSede(2);
        model.addAttribute("listaUsuarioDoctores", listaDoctoresS);
        return "administrador/dashboarddoctor";
    }

    @GetMapping(value = "/configuraciones")
    public String config(Model model) {
        Sede sede = sedeRepository.findByIdsede(1);
        model.addAttribute("sede",sede);
        return "administrador/configuraciones";
    }

    @GetMapping(value = "/dashboardfinanzas")
    public String dashboardfinanz() {

        return "administrador/dashboardfinanzas";
    }

    @GetMapping(value = "/historialclinico")
    public String historialClinico() {

        return "administrador/historialclinico";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente() {

        return "administrador/crearpaciente";
    }

    @GetMapping(value = "/invitar")
    public String invitar() {

        return "administrador/invitar";
    }

    @GetMapping(value = "/vistaformato")
    public String vistaForm() {

        return "administrador/vistaformato";
    }

    @GetMapping(value = "/notificaciones")
    public String notif() {

        return "administrador/notificaciones";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes() {

        return "administrador/mensajes";
    }

    @GetMapping(value = "/chat")
    public String chat() {

        return "administrador/chat";
    }

    @GetMapping(value = "/detallesdoctor")
    public String Detalles() {

        return "administrador/detallesdoctor";
    }

    @GetMapping(value = "/vistacuestionario")
    public String VerCuestionario() {

        return "administrador/vistacuestionario";
    }

    @GetMapping(value = "/creardoctor")
    public String crearDoctor(@ModelAttribute("especialidad") Especialidad especialidad, Model model) {
        model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
        return "administrador/creardoctor";
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

    @PostMapping("/editarperfil")
    @Transactional
    public String editarPerfil(@RequestParam("idusuario") int idUsuario,
                               @RequestParam("nombres") String nombres,
                               @RequestParam("apellidos") String apellidos,
                               @RequestParam("correo") String correo,
                               @RequestParam("celular") String celular, RedirectAttributes redirectAttributes){
        usuarioRepository.perfil(nombres,apellidos,correo,celular);
        redirectAttributes.addAttribute("id",idUsuario);
        return "redirect:/perfil";
    }



    @GetMapping(value = "/nuevo")
    public String nuevoPaciente(){
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














}


