package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import com.example.proyectogticsgrupo1.Service.EmailService;
/*import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;*/
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.Doc;
import java.io.File;
import java.io.FileOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Autowired
    private DatosJsonRepository datosJsonRepository;

    @Autowired
    private BoletaDoctorRepository boletaDoctorRepository;


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
        model.addAttribute("listaIngresosEgresos", boletaDoctorRepository.IngresosEgresosDTO());
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

    private String obtenerColorEvento(String nombreEspecialidad) {
        // Lógica de mapeo para asociar nombres de especialidad con colores específicos
        switch (nombreEspecialidad) {
            case "Cardiología":
                return "red";
            case "Traumatología":
                return "green";
            case "Cirugía cardiovascular":
                return "purple";
            case "Laboratorio":
                return "orange";
            case "Rayos X":
                return "blue";
            case "Ecografía":
                return "teal";
            case "Ginecología":
                return "pink";
            case "Urología":
                return "brown";
            case "Medicina Interna":
                return "navy";
            case "Neumología":
                return "lightblue";
            case "Pediatría":
                return "lime";
            case "Neurología":
                return "dark blue";
            case "Gastroenterología":
                return "olive";
            case "Endocrinología":
                return "maroon";
            case "Otorrinolaringología":
                return "aqua";
            case "Nefrología":
                return "silver";
            case "Dermatología":
                return "fuchsia";
            default:
                return "gray"; // Color predeterminado para otros casos
        }
    }



    @GetMapping(value = "/calendariogeneral")
    public String genCalendar(@ModelAttribute("administradorlog") Usuario usuario, Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        Usuario administrador = usuarioRepository.buscarPorId(usuarioAdministrador.getIdusuario());
        model.addAttribute("administradorlog", administrador);

        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();

        // Obtener el día de la semana actual (Domingo = 1, Lunes = 2, ..., Sábado = 7)
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();

        // Obtener las citas de la sede para el día correspondiente
        List<Cita> citas = citaRepository.citaPorSede2(usuarioAdministrador.getSede().getIdsede());

        // Crear un mapa para almacenar las citas por día y hora
        Map<LocalDate, Map<Integer, List<Cita>>> citasPorDiaYHora = new HashMap<>();

        // Agrupar las citas por día y hora
        for (Cita cita : citas) {
            LocalDate fecha = cita.getFecha();
            int hourOfDay = cita.getHorainicio().getHour();

            Map<Integer, List<Cita>> citasPorHora = citasPorDiaYHora.computeIfAbsent(fecha, k -> new HashMap<>());
            citasPorHora.computeIfAbsent(hourOfDay, k -> new ArrayList<>()).add(cita);
        }

        // Crear una lista de eventos en formato JSON
        List<Map<String, Object>> eventos = new ArrayList<>();
        for (Map<Integer, List<Cita>> citasPorHora : citasPorDiaYHora.values()) {
            for (List<Cita> citasDeHora : citasPorHora.values()) {
                for (Cita cita : citasDeHora) {
                    Map<String, Object> evento = new HashMap<>();
                    evento.put("title", cita.getHorainicio() + " " + cita.getEspecialidad().getNombre()); // Título del evento
                    evento.put("start", cita.getFecha().toString()); // Fecha de inicio del evento
                    evento.put("color", obtenerColorEvento(cita.getEspecialidad().getNombre())); // Color del evento

                    eventos.add(evento);
                }
            }
        }

        // Agregar la lista de eventos al modelo
        model.addAttribute("eventos", eventos);

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
        List<ModeloJsonEntity> modeloEntityList = modeloJsonRepository.listarPlantillas();
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
        model.addAttribute("listaIngresosEgresos", boletaDoctorRepository.IngresosEgresosDTO());
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
        List<DatosJsonEntity> datosPacientes = datosJsonRepository.listarparapaciente(paciente.getUsuario().getIdusuario());
        model.addAttribute("listaparapaciente",datosPacientes);
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
    @Transactional
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
    public String perfilPaciente(@ModelAttribute("administradorlog") Usuario usuario, Model model) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuario");
        Usuario administrador = usuarioRepository.buscarPorId(usuarioAdministrador.getIdusuario());
        model.addAttribute("administradorlog", administrador);
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

    @PostMapping(value = "/enviarmensaje")
    public String enviarMensaje(@RequestParam("correo") String correo,
                                @RequestParam("asunto") String asunto,
                                @RequestParam("descripcion") String descripcion,
                                RedirectAttributes redirectAttributes) {
        // Verificar si el correo existe en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario != null) {
            // Crear un nuevo mensaje y asignar los valores
            MailCorreo mensaje = new MailCorreo();
            mensaje.setAsunto(asunto);
            mensaje.setDescripcion(descripcion);
            mensaje.setCorreodestino(correo);
            mensaje.setCorreo(usuario.getCorreo());
            Usuario usuarioo = new Usuario();
            usuarioo.setIdusuario(2);
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

        return "redirect:/administrador/chat";
    }


    private static File convertFile(MultipartFile file) {

        try {
            if (file.getOriginalFilename() == null) {
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            return convertedFile;
        } catch (Exception e) {
            throw new RuntimeException("An error has occurred while converting the file");
        }
    }

    private static String checkFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3"};

            for (String extension : extensionList) {
                if (fileName.endsWith(extension)) {
//                    LOGGER.debug("Accepted file type : {}", extension);
                    return extension;
                }
            }
        }
        return ".jpeg";
    }
}