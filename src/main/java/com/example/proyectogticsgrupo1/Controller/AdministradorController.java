package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import com.example.proyectogticsgrupo1.Service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping(value="/administrador")

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

    @Autowired
    private EmailService emailService;
    @Autowired
    private MailCorreoRepository mailCorreoRepository;

    @Autowired
    private ModeloJsonRepository modeloJsonRepository;

    @Autowired
    private NotificacionesRepository notificacionesRepository;


    @GetMapping(value = "/email")
    public String emailpr() {
        String user = "angieealejandro@gmail.com";
        String subj = "HOLA";
        String msj = "Pruebas de envio";
        emailService.sendEmail(user, subj, msj);
        return "redirect:/administrador";
    }

    @GetMapping("")
    public String administrador(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        List<Paciente> listaPacientesSD = pacienteRepository.listadopacientes(usuarioAdministrador.getSede().getIdsede()); // a futuro cambiar
        model.addAttribute("listaUsuariosPacientes", listaPacientesSD);
        List<Doctor> listaDoctoresSD = doctorRepository.listarDoctorporSedeDashboard(usuarioAdministrador.getSede().getIdsede());
        model.addAttribute("listaUsuarioDoctores", listaDoctoresSD);
        model.addAttribute("usuario", usuarioAdministrador);
        return "administrador/dashboard";
    }

    @GetMapping(value = "/nuevopaciente")
    public String creandoPaciente(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        return "administrador/nuevopaciente";

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
    @Transactional
    public String guardarUsuario(@ModelAttribute("usuario2") @Valid Usuario user,BindingResult bindingResult, RedirectAttributes attr, Model model, @RequestParam("direccion") String direccion) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);

        Usuario existingUser = usuarioRepository.findByDni(user.getDni());
        if (existingUser != null && !existingUser.getIdusuario().equals(user.getIdusuario())) {
            bindingResult.rejectValue("dni", "error.dni", "Ya existe un usuario con este DNI");
        }

        Usuario existingUserCelular = usuarioRepository.findByCelular(user.getCelular());
        if (existingUserCelular != null && !existingUserCelular.getIdusuario().equals(user.getIdusuario())) {
            bindingResult.rejectValue("celular", "error.celular", "Ya existe un usuario con este número de celular");
        }

        Usuario existingUserCorreo = usuarioRepository.findByCorreo(user.getCorreo());
        if (existingUserCorreo != null && !existingUserCorreo.getIdusuario().equals(user.getIdusuario())) {
            bindingResult.rejectValue("correo", "error.correo", "Ya existe un usuario con este correo electrónico");
        }

        if (user.getIdusuario() == null) {
            attr.addFlashAttribute("pac", "Paciente creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Paciente actualizado exitosamente");
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("direccion", direccion);
            return "administrador/crearpaciente";
        }else {
            Tipodeusuario tipodeusuario = new Tipodeusuario();
            tipodeusuario.setIdtipodeusuario(4);
            user.setEstadohabilitado(1);
            user.setTipodeusuario(tipodeusuario);
            Sede sede = new Sede();
            sede.setIdsede(usuarioAdministrador.getSede().getIdsede());
            user.setSede(sede);
            String contrasenaGenerada = generarContrasena(10);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String contrasenaCifrada = passwordEncoder.encode(contrasenaGenerada);
            user.setContrasena(contrasenaCifrada);
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
            String content = "Usted registro un usuario de TIPO: PACIENTE, con CORREO: " + paciente.getUsuario().getCorreo() ;
            String titulo = "Usuario creado con exito";
            notificacionesRepository.notificarCreacion(usuarioAdministrador.getIdusuario(),content,titulo);
            emailService.sendEmail(paciente.getUsuario().getCorreo(), "Confirmación de Registro", "Estimado usuario, usted ha sido registrado en:\nSede " + usuarioAdministrador.getSede().getNombre() + "\nUbicada en " + usuarioAdministrador.getSede().getDireccion() + "\nTu contraseña por defecto es: " + contrasenaGenerada + "\nIngresa"+ "aquí" +"para cambiarla : http://localhost:8081/cambiarcontrasena");
        }
        return "redirect:/administrador/dashboardpaciente";
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

        // Obtener la fecha actual
        Date currentDate = new Date();

        // Obtener el día de la semana actual (Domingo = 1, Lunes = 2, ..., Sábado = 7)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        String currentDayOfWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

        // Obtener las citas de la sede para el día correspondiente
        List<Cita> citas = citaRepository.citaPorSede(usuarioAdministrador.getIdusuario(), currentDayOfWeek);

        // Obtener las especialidades de las citas
        List<Especialidad> especialidades = new ArrayList<>();
        for (Cita cita : citas) {
            Especialidad especialidad = especialidadRepository.findByIdespecialidad(cita.getEspecialidad().getIdespecialidad());
            especialidades.add(especialidad);
        }

        // Pasar las citas y las especialidades al modelo
        model.addAttribute("citas", citas);
        model.addAttribute("especialidades", especialidades);

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
        List<ModeloJsonEntity> modeloEntityList = modeloJsonRepository.findAll();
        model.addAttribute("modeloEntityList",modeloEntityList);

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
    public String porRegistrar(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        List<Paciente> listaPacientesR = pacienteRepository.listarpinvitado(usuarioAdministrador.getSede().getIdsede()); // a futuro cambiar
        model.addAttribute("listaUsuariosPInvitados", listaPacientesR);
        return "administrador/porregistrar";
    }

    @PostMapping(value = "/actualizar")
    public String actualizarEstadoPacientes() {
        pacienteRepository.actualizarEstado();
        return "redirect:/administrador/dashboardpaciente";
    }

    @PostMapping("/buscarInvitado")
    public String buscadorInvitados(@RequestParam("buscando") String buscando, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("buscando", buscando);
        return "redirect:/administrador/porregistrar";
    }

    @GetMapping(value = "/dashboardpaciente")
    public String listaCitas(Model model, @RequestParam(name = "buscando", required = false) String buscando) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        List<Paciente> listaPacientesS = pacienteRepository.listadopacientesdashboard(usuarioAdministrador.getSede().getIdsede()); // a futuro cambiar
        model.addAttribute("listaUsuariosPacientes", listaPacientesS);
        return "administrador/dashboardpaciente";
    }


    @PostMapping("/buscarPaciente")
    public String buscadorPacientess(@RequestParam("buscando") String buscando, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("buscando", buscando);
        return "redirect:/administrador/dashboardpaciente";
    }

    @GetMapping(value = "/dashboarddoctor")
    public String dashboarddoc(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        List<Doctor> listaDoctoresS = doctorRepository.listarDoctorporSede(usuarioAdministrador.getSede().getIdsede());
        model.addAttribute("listaUsuarioDoctores", listaDoctoresS);
        return "administrador/dashboarddoctor";
    }

    @PostMapping("/buscarDoctor")
    public String buscadorDoctor(@RequestParam("buscando") String buscando, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("buscando", buscando);
        return "redirect:/administrador/dashboarddoctor";
    }

    @GetMapping(value = "/configuraciones")
    public String config(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        Sede sede = sedeRepository.findByIdsede(usuarioAdministrador.getSede().getIdsede());
        model.addAttribute("sede", sede);
        return "administrador/configuraciones";

    }

    @GetMapping(value = "/dashboardfinanzas")
    public String dashboardfinanz(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        return "administrador/dashboardfinanzas";

    }

    @GetMapping("/historialclinico")
    public String historialClinico(Model model, @RequestParam("id") int idPaciente) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        Paciente paciente = pacienteRepository.buscarPacientH(idPaciente);
        model.addAttribute("paciente", paciente);
        List<Cita> citasFuturas = citaRepository.findByPacienteAndFechaAfterOrderByFechaAsc(paciente, LocalDate.now());
        model.addAttribute("citas", citasFuturas);
        return "administrador/historialclinico";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente(@ModelAttribute("usuario2") Usuario usuario2, Model model) {
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
        model.addAttribute("notificaciones",notificacionesRepository.notificacionesPorUsuario(usuarioAdministrador.getIdusuario()));
        return "administrador/notificaciones";
    }


    @GetMapping(value = "/mensajes")
    public String mensajes(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        model.addAttribute("listamensajes", mailCorreoRepository.buscarMensajesEnviadorPorID(usuarioAdministrador.getIdusuario()));
        return "administrador/mensajes";
    }

    @GetMapping(value = "/chat")
    public String chat(Model model, @RequestParam("idM") int idMensaje) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        model.addAttribute("mensaje", mailCorreoRepository.buscarMensajePorID(idMensaje));
        return "administrador/chat";

    }


    @GetMapping(value = "/detallesdoctor")
    public String Detalles(Model model, @RequestParam("id") int idDoctor) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        Doctor doctor = doctorRepository.buscarDoctorH(idDoctor);
        model.addAttribute("doctor", doctor);
        return "administrador/detallesdoctor";

    }

    @GetMapping(value = "/vistacuestionario")
    public String VerCuestionario(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        return "administrador/vistacuestionario";
    }

    @GetMapping(value = "/creardoctor")
    public String crearDoctor(@ModelAttribute("usuario1") Usuario usuario1, Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
        return "administrador/creardoctor";
    }


    @PostMapping(value = "/guardar3")
    public String guardarDoctor(@ModelAttribute("usuario1") @Valid Usuario user, BindingResult bindingResult, RedirectAttributes attr, Model model, @RequestParam("especialidad") int idEspecialidad) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);

        Usuario existingUser = usuarioRepository.findByDni(user.getDni());
        if (existingUser != null && !existingUser.getIdusuario().equals(user.getIdusuario())) {
            bindingResult.rejectValue("dni", "error.dni", "Ya existe un usuario con este DNI");
        }

        Usuario existingUserCelular = usuarioRepository.findByCelular(user.getCelular());
        if (existingUserCelular != null && !existingUserCelular.getIdusuario().equals(user.getIdusuario())) {
            bindingResult.rejectValue("celular", "error.celular", "Ya existe un usuario con este número de celular");
        }

        Usuario existingUserCorreo = usuarioRepository.findByCorreo(user.getCorreo());
        if (existingUserCorreo != null && !existingUserCorreo.getIdusuario().equals(user.getIdusuario())) {
            bindingResult.rejectValue("correo", "error.correo", "Ya existe un usuario con este correo electrónico");
        }

        if (user.getIdusuario() == null) {
            attr.addFlashAttribute("doc", "Doctor creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Doctor actualizado exitosamente");
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
            return "administrador/creardoctor";
        }else {
            Tipodeusuario tipodeusuario = new Tipodeusuario();
            tipodeusuario.setIdtipodeusuario(5);
            user.setEstadohabilitado(1);
            user.setTipodeusuario(tipodeusuario);
            Sede sede = new Sede();
            sede.setIdsede(usuarioAdministrador.getSede().getIdsede());
            user.setSede(sede);
            String contrasenaGenerada = generarContrasena(10);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String contrasenaCifrada = passwordEncoder.encode(contrasenaGenerada);
            user.setContrasena(contrasenaCifrada);
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
            String content = "Usted registro un usuario de TIPO: DOCTOR, con CORREO: " + doctor.getUsuario().getCorreo() ;
            String titulo = "Usuario creado con exito";
            notificacionesRepository.notificarCreacion(usuarioAdministrador.getIdusuario(),content,titulo);
            emailService.sendEmail(doctor.getUsuario().getCorreo(), "Confirmación de Registro", "Estimado usuario, usted ha sido registrado en:\nSede " + usuarioAdministrador.getSede().getNombre() + "\nUbicada en " + usuarioAdministrador.getSede().getDireccion() + "\nTu contraseña por defecto es: " + contrasenaGenerada + "\nIngresa"+ " aquí" +"para cambiarla : http://localhost:8081/cambiarcontrasena");
            return "redirect:/administrador/dashboarddoctor";
        }
    }


    @GetMapping(value = "/perfil")
    public String perfilPaciente(Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrador);
        return "administrador/perfil";
    }

    @PostMapping(value = "/editarperfil")
    public String editarPerfil(RedirectAttributes redirectAttributes,
                               @RequestParam("id") int idusuario,
                               @RequestParam("nombres") String nombres,
                               @RequestParam("apellidos") String apellidos,
                               @RequestParam("correo") String correo,
                               @RequestParam("celular") String celular) {


        usuarioRepository.perfil(nombres, apellidos, correo, celular, idusuario);
        session.removeAttribute("usuario");
        session.setAttribute("usuario", usuarioRepository.findById(idusuario).get());
        redirectAttributes.addAttribute("id", idusuario);
        redirectAttributes.addFlashAttribute("msg", "Perfil Actualizado");
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


    @PostMapping(value = "/changepassword")
    @Transactional
    public String changePassword(@RequestParam("id") int idusuario,@RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes) {

        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if (passwordEncoder.matches(contrasena, usuarioAdministrador.getContrasena())) {
            String hashedNewPassword = passwordEncoder.encode(newpassword);


            //usuarioRepository.changePassword(renewpassword, usuarioAdministrador.getIdusuario());
            usuarioRepository.changePassword(hashedNewPassword, usuarioAdministrador.getIdusuario());
            redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada");

        } else {
            redirectAttributes.addFlashAttribute("psw2", "La contraseña es incorrecta");
        }

        return "redirect:/administrador/perfil";
    }
}