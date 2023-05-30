package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import com.example.proyectogticsgrupo1.Service.EmailService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/paciente")
public class PacienteController {

    final SedeRepository sedeRepository;
    final EspecialidadRepository especialidadRepository;
    final DoctorRepository doctorRepository;
    final UserRepository userRepository;

    @Autowired
    final PacienteRepository pacienteRepository;
    final TipoCitaRepository tipoCitaRepository;
    final CitaRepository citaRepository;
    final EventocalendariodoctorRepository eventocalendariodoctorRepository;

    final BoletaDoctorRepository boletaDoctorRepository;

    final SeguroRepository seguroRepository;

    final BoletaPacienteRepository boletaPacienteRepository;

    public PacienteController(SedeRepository sedeRepository, EspecialidadRepository especialidadRepository, DoctorRepository doctorRepository, UserRepository userRepository, PacienteRepository pacienteRepository, TipoCitaRepository tipoCitaRepository, CitaRepository citaRepository, EventocalendariodoctorRepository eventocalendariodoctorRepository,
                              BoletaDoctorRepository boletaDoctorRepository,SeguroRepository seguroRepository,BoletaPacienteRepository boletaPacienteRepository) {
        this.sedeRepository = sedeRepository;
        this.especialidadRepository = especialidadRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.pacienteRepository = pacienteRepository;
        this.tipoCitaRepository = tipoCitaRepository;
        this.citaRepository = citaRepository;
        this.eventocalendariodoctorRepository = eventocalendariodoctorRepository;
        this.boletaDoctorRepository = boletaDoctorRepository;
        this.seguroRepository = seguroRepository;
        this.boletaPacienteRepository = boletaPacienteRepository;
    }

    @Autowired
    private HttpSession session;


    @GetMapping(value = "/")
    public String paciente( Model model, @RequestParam(value = "esp", required = false) Integer esp, @RequestParam(value = "msg1", required = false) Integer msg1,RedirectAttributes redirectAttributes){
;
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        List<Especialidad> listespecialidad = especialidadRepository.findAll();

        model.addAttribute("pacientelog",pacienteRepository.pacXuser(usuario.getIdusuario()));
        model.addAttribute("especialidades", listespecialidad);
        model.addAttribute("citashoy", citaRepository.citasHoy(pacienteRepository.pacXuser(usuario.getIdusuario()).getIdpaciente()));
        model.addAttribute("sedes", sedeRepository.findAll());
        redirectAttributes.addFlashAttribute("msg1", "Por el momento no contamos con doctores en esa especialidad");
        if(msg1!=null){
            redirectAttributes.addFlashAttribute("msg2", "Ha reservado una cita con exito");

        }

        if(esp!=null){
            System.out.println("no nulo esp");
            if(doctorRepository.doctoresPorEsp(esp).size()>=1){
                System.out.println("lista no vaci");
                model.addAttribute("docs",doctorRepository.doctoresPorEsp(esp));
            }
            else{
                System.out.println("lista vacia");
                redirectAttributes.addFlashAttribute("msg", "Por el momento no contamos con doctores en esa especailiad");
                return "redirect:/paciente/";
            }
        }
        else {
            System.out.println("esp null");
            model.addAttribute("docs", doctorRepository.findAll());
        }
        return "paciente/index";

    }

    @Autowired
    private EmailService emailService;
    @GetMapping(value = "/email")
    public String emailpr(){
        String user = "sebastian.segura1310@gmail.com";
        String subj = "HOLA";
        String msj = "Pruebas de envio";
        emailService.sendEmail(user,subj,msj);
        return "redirect:/paciente/";
    }

    @GetMapping(value = "/perfilDoctor")
    public String perfilDoc(RedirectAttributes redirectAttributes, @RequestParam("iddoc") Integer iddoc, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        model.addAttribute("pacientelog",paciente);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(iddoc);
        if(optionalDoctor.isPresent()){
            Doctor doctor=optionalDoctor.get();
            model.addAttribute("doc",doctor);
            if(eventocalendariodoctorRepository.getDiasProx(doctor.getIddoctor()).size()>=1){
                model.addAttribute("dias",eventocalendariodoctorRepository.getDiasProx(doctor.getIddoctor()));
            }
            return "paciente/perfilDoctor";
        }
        else {
            return "redirect:/paciente/";
        }

    }
    @GetMapping(value = "/selecTipoCita")
    public String selecTipoCita(Model model,@RequestParam("iddoc") Integer id){
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        model.addAttribute("pacientelog",paciente);
        model.addAttribute("doc", doctorRepository.findById(id).get());
        return "paciente/tipocita";
    }
    @GetMapping(value = "/reservar2")
    public String selectDate(Model model, @RequestParam("iddoc") Integer id){
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Doctor doc = doctorRepository.buscarDoctorPorId(id);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        model.addAttribute("tipocita",tipoCitaRepository.findAll());
        model.addAttribute("lunes",eventocalendariodoctorRepository.listalunes(doc.getIddoctor()));
        model.addAttribute("martes",eventocalendariodoctorRepository.listaMartes(id));
        model.addAttribute("miercoles",eventocalendariodoctorRepository.listaMiercoles(id));
        model.addAttribute("jueves",eventocalendariodoctorRepository.listaJueves(id));
        model.addAttribute("viernes",eventocalendariodoctorRepository.listaViernes(id));
        model.addAttribute("sabado",eventocalendariodoctorRepository.listaSabado(id));

        model.addAttribute("pacientelog",paciente);
        //Eventocalendariodoctor eventocalendariodoctor = eventocalendariodoctorRepository.calendarioPorDoctor(id);
        model.addAttribute("calendario", eventocalendariodoctorRepository.calendarioPorDoctor(id));
        return "paciente/reservar2";
    }

    @GetMapping(value = "/pagos")
    public String pagosView(Model model){
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        model.addAttribute("pacientelog",paciente);
        if(citaRepository.citasPorPagar(paciente.getIdpaciente()).size()>=1){
            model.addAttribute("pagos",citaRepository.citasPorPagar(paciente.getIdpaciente()));
        }
        return "paciente/pagos";
    }

    @GetMapping(value = "/perfil")
    public String perfilPaciente(Model model){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/perfil";
    }

    @GetMapping(value = "/agendarCita")
    public String agendarCita(Model model){
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
       // Paciente paciente =  optionalPaciente.get();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        List<Sede> listsede = sedeRepository.findAll();
        List<Especialidad> listespecialidad = especialidadRepository.findAll();
        model.addAttribute("sedesparacitas", listsede);
        model.addAttribute("especialidades", listespecialidad);
        model.addAttribute("pacientelog",paciente);
        model.addAttribute("doctores",doctorRepository.findAll());
        model.addAttribute("tipocita",tipoCitaRepository.findAll());
        model.addAttribute("caldisponible", eventocalendariodoctorRepository.calendarioDoctorDisponible());
        return "paciente/agendarCita";
    }
    @GetMapping(value = "/historialCitas")
    public String historialCitas(Model model){
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        model.addAttribute("listcitas", citaRepository.citaPorPaciente(paciente.getIdpaciente()));
        model.addAttribute("pacientelog",paciente);
        return "paciente/historialCitas";
    }

    @GetMapping(value = "/calendarioMensual")
    public String calendarioMensual(Model model){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();
        if(citaRepository.citasJueves()!=null){
            model.addAttribute("cjeuves", citaRepository.citasJueves());
        }
        model.addAttribute("pacientelog",paciente);
        return "paciente/calendarioMensual";

    }

    @GetMapping(value = "/chat")
    public String chat(Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);

        return "paciente/chat";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes(Model model) {
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        model.addAttribute("pacientelog",paciente);

        return "paciente/mensajes";
    }

    @GetMapping(value = "/notificaciones")
    public String notif(Model model) {
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        model.addAttribute("pacientelog",paciente);

        return "paciente/notificaciones";
    }

    @PostMapping(value = "/alergia")
    @Transactional
    public String modAlergia(@RequestParam("alergias") String alergia, @RequestParam("idpaciente") Integer id, RedirectAttributes redirectAttributes){
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        String alergias = paciente.getAlergias();
        if(alergias.contains(alergia.toLowerCase())){
            redirectAttributes.addFlashAttribute("msg1","Esa alergia ya se encuentra registrada");
        }else {
            String alg = paciente.getAlergias()+","+alergia.toLowerCase();
            pacienteRepository.modificarAlergia(alg, id);
            redirectAttributes.addFlashAttribute("msg2","Alergia agregada correctamente");
        }
        return "redirect:/paciente/perfil";
    }



    @PostMapping(value = "/changepassword")
    @Transactional
    public String changePassword(@RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



        if(passwordEncoder.matches(contrasena, paciente.getUsuario().getContrasena())){
            String hashedNewPassword = passwordEncoder.encode(newpassword);

            userRepository.changePassword(hashedNewPassword,paciente.getUsuario().getIdusuario());
            redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada");

        }else {
            redirectAttributes.addFlashAttribute("psw2", "La contraseña es incorrecta");
        }
        return "redirect:/paciente/perfil";
    }

    @PostMapping(value = "/pruebascita")
    @Transactional
    public String pruebascita(@RequestParam("idsede") Integer idsede,
                              @RequestParam("especialidadid") Integer idesp,
                              @RequestParam("iddoctor") Integer iddoctor,
                              @RequestParam("fecha") LocalDate fecha ,
                              @RequestParam("hora")LocalTime hora,
                              @RequestParam("idseguro") Integer idseguro,
                              @RequestParam("idtipocita") Integer idtipocita, RedirectAttributes redirectAttributes){

        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        if(idsede==doctorRepository.findById(iddoctor).get().getSede().getIdsede()){
            System.out.println("aca?");
            if(idesp==doctorRepository.findById(iddoctor).get().getEspecialidad().getIdespecialidad()){
                System.out.println("revis la hora");
                Boolean flg1=false;
                int i;
                System.out.println("size: " + eventocalendariodoctorRepository.calendarioFecha(fecha,iddoctor).size());
                if(eventocalendariodoctorRepository.calendarioFecha(fecha,iddoctor).size()<=1){
                    System.out.println("entro a 0");
                    i=0;
                }else {
                    System.out.println("entro a 1");
                    i=0;
                }
                while (i<eventocalendariodoctorRepository.calendarioFecha(fecha,iddoctor).size()){
                    System.out.println("hora " +i + eventocalendariodoctorRepository.calendarioFecha(fecha,iddoctor).get(i).getHorainicio());
                    if(eventocalendariodoctorRepository.calendarioFecha(fecha,iddoctor).get(i).getHorainicio()==hora){
                        flg1=true;
                    }
                    i++;
                }
                System.out.println("salio");
                System.out.println(flg1);
                if(flg1){
                    System.out.println("entra a validar si se repite");
                    Boolean flg = true;
                    for(Cita c:citaRepository.citasRepetidasValidacion(1,fecha)){
                        if(c.getHorainicio()==hora){
                            flg=false;
                            break;
                        }
                    }
                    if(flg){
                        System.out.println("llega aca prim");

                        citaRepository.agengedarcita(idsede, idesp,fecha, hora, hora.plusHours(1),60, idtipocita, idseguro, 1, paciente.getIdpaciente(),iddoctor);
                        Double costoEspecialidad = especialidadRepository.getCosto(idesp);
                        Double comisionDoctor = seguroRepository.getCosto(idseguro);
                        Double coaseguroPaciente = seguroRepository.getCoaseguro(idseguro);
                        Float montoDoctor = (float) (costoEspecialidad * comisionDoctor);
                        Float montoPaciente = (float) (costoEspecialidad * coaseguroPaciente);
                        Cita citaAgendada = citaRepository.citaAgendada(fecha,hora);
                        boletaDoctorRepository.generarBoletaDoctorCita(citaAgendada.getIdcita(),paciente.getIdpaciente(),idseguro,iddoctor,montoDoctor);
                        boletaPacienteRepository.generarBoletaPacienteCita(paciente.getIdpaciente(),citaAgendada.getIdcita(),idseguro,montoPaciente);
                        eventocalendariodoctorRepository.cambiarEstadoCalendario(iddoctor,fecha,hora);
                        if(idtipocita==1){
                            emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita para el "+fecha.toString()+ ".\n"+"En la sede "+sedeRepository.findById(idsede).get().getNombre()+" ubicada " +sedeRepository.findById(idsede).get().getDireccion());

                        }else{
                            emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita virtual para el "+fecha.toString()+ ".\n"+"El link para la sesion de zoom es el siguiente: " + doctorRepository.findById(iddoctor).get().getZoom());

                        }
                        redirectAttributes.addFlashAttribute("msg1", "Ha reservado una cita con éxito");

                        return "redirect:/paciente/";
                    }
                    else {
                        redirectAttributes.addFlashAttribute("msg", "Se tiene un cita en la hora escogida");
                        return "redirect:/paciente/agendarCita";
                    }
                }
                else {
                    redirectAttributes.addFlashAttribute("msg", "La hora escogida no es valida");
                    return "redirect:/paciente/agendarCita";
                }
            }
            else{
                redirectAttributes.addFlashAttribute("msg", "El doctor no pertenece a esa especialidad");
                return "redirect:/paciente/agendarCita";
            }
        }else {
            redirectAttributes.addFlashAttribute("msg", "El doctor debe pertenecer a la sede");
            return "redirect:/paciente/agendarCita";
        }
    }

    @PostMapping(value = "/pagospruebas")
    public String pagosPr(@RequestParam(value = "citapagos", required = false) Integer []id, RedirectAttributes redirectAttributes){
        System.out.println("ids:  " + id.length);
        if(id.length>0){
            List<Cita> lisaCitas = new ArrayList<>();
            Double costos = 0.0;
            for(int i=0;i<id.length;i++){
                lisaCitas.add(citaRepository.findById(id[i]).get());
                costos=costos+lisaCitas.get(i).getEspecialidad().getCosto();
            }
            redirectAttributes.addFlashAttribute("msj",costos);
        }
        return "redirect:/paciente/pagos";
    }

    @PostMapping(value = "/reservar2")
    @Transactional
    public String reserva2 (@RequestParam("idev") Integer idev, @RequestParam("idtipocita") Integer idtipocita, RedirectAttributes redirectAttributes){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        Eventocalendariodoctor eventocalendariodoctor = eventocalendariodoctorRepository.findById(idev).get();
        Doctor doc = doctorRepository.findById(eventocalendariodoctor.getDoctor().getIddoctor()).get();
        System.out.println("entraaa\n");
        System.out.println("doctooooor");
        System.out.println(doc.getIddoctor());
        citaRepository.agengedarcita(doc.getSede().getIdsede(),
                doc.getEspecialidad().getIdespecialidad(),
                eventocalendariodoctor.getFecha(),
                eventocalendariodoctor.getHorainicio(),
                eventocalendariodoctor.getHorainicio().plusHours(1),
                60,
                idtipocita,
                paciente.getIdpaciente(),
                1,
                paciente.getIdpaciente(),
                doc.getIddoctor());
        eventocalendariodoctorRepository.cambiarEstadoCalendario(doc.getIddoctor(),
                eventocalendariodoctor.getFecha(),
                eventocalendariodoctor.getHorainicio());

        if(idtipocita==1){
            emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita para el "+eventocalendariodoctor.getFecha().toString()+ ".\n"+"En la sede "+sedeRepository.findById(doc.getSede().getIdsede()).get().getNombre()+" ubicada " +sedeRepository.findById(doc.getSede().getIdsede()).get().getDireccion());

        }else{
            emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita virtual para el "+eventocalendariodoctor.getFecha().toString()+ ".\n"+"El link para la sesion de zoom es el siguiente: " +doc.getZoom());

        }
        redirectAttributes.addFlashAttribute("msg1", "Ha reservado una cita con éxito");

        return "redirect:/paciente/";
    }

}
