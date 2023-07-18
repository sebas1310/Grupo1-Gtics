package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.GMailer;
import com.example.proyectogticsgrupo1.Repository.*;
import com.example.proyectogticsgrupo1.Service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/paciente")
public class PacienteController {

    final SedeRepository sedeRepository;
    final EspecialidadRepository especialidadRepository;
    final DoctorRepository doctorRepository;
    final UserRepository userRepository;

    final UsuarioRepository usuarioRepository;
    final NotificacionesRepository notificacionesRepository;

    final RecetaMedicaRepository recetaMedicaRepository;

    @Autowired
    final PacienteRepository pacienteRepository;
    final TipoCitaRepository tipoCitaRepository;
    final CitaRepository citaRepository;
    final EventocalendariodoctorRepository eventocalendariodoctorRepository;

    final BoletaDoctorRepository boletaDoctorRepository;

    final SeguroRepository seguroRepository;

    final BoletaPacienteRepository boletaPacienteRepository;

    @Autowired
    TablaDatosLlenosRepository tablaDatosLlenosRepository;
    TablaDatosLlenos tablaDatosLlenos;


    public PacienteController(SedeRepository sedeRepository, EspecialidadRepository especialidadRepository, DoctorRepository doctorRepository, UserRepository userRepository, NotificacionesRepository notificacionesRepository, RecetaMedicaRepository recetaMedicaRepository, PacienteRepository pacienteRepository, TipoCitaRepository tipoCitaRepository, CitaRepository citaRepository, EventocalendariodoctorRepository eventocalendariodoctorRepository,
                              BoletaDoctorRepository boletaDoctorRepository, SeguroRepository seguroRepository, BoletaPacienteRepository boletaPacienteRepository, UsuarioRepository usuarioRepository) {
        this.sedeRepository = sedeRepository;
        this.especialidadRepository = especialidadRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.notificacionesRepository = notificacionesRepository;
        this.recetaMedicaRepository = recetaMedicaRepository;
        this.pacienteRepository = pacienteRepository;
        this.tipoCitaRepository = tipoCitaRepository;
        this.citaRepository = citaRepository;
        this.eventocalendariodoctorRepository = eventocalendariodoctorRepository;
        this.boletaDoctorRepository = boletaDoctorRepository;
        this.seguroRepository = seguroRepository;
        this.boletaPacienteRepository = boletaPacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private HttpSession session;


    @Autowired
    DatosJsonRepository datosJsonRepository;


    @Autowired
    ModeloXCitaRepository modeloXCitaRepository;

    public Map<String, Integer> cuestToReply() {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        List<Cita> citapac = citaRepository.citasParaCuestionario(paciente.getIdpaciente());

        Integer idcuest = 0;
        Integer idcita = null;
        Integer idmc=null;
        for (Cita c : citapac) {
            ModeloXCita modeloXCita = modeloXCitaRepository.porllenar(c.getIdcita());
            if (modeloXCita != null) {
                idcuest = modeloXCita.getIdmodelofk();
                idcita = c.getIdcita();
                idmc=modeloXCita.getId();
                break;
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("idcuest", idcuest);
        result.put("idcita", idcita);
        result.put("id",idmc);
        return result;
    }


    @Transactional
    @GetMapping(value = "/")
    public String paciente( Model model, @RequestParam(value = "esp", required = false) Integer esp, @RequestParam(value = "msg1", required = false) Integer msg1,RedirectAttributes redirectAttributes){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Especialidad> listespecialidad = especialidadRepository.findAll();
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        int estado = paciente.getEstadoPaciente().getIdestadopaciente();

        Map<String, Integer> resultado = cuestToReply();
        Integer idCuestionario = resultado.get("idcuest");
        Integer idcitatoreply = resultado.get("idcita");
        Integer idmc=resultado.get("id");
        System.out.println("a llenar "+idcitatoreply+" "+idCuestionario);
        System.out.println("id: " + idmc);

        if(idCuestionario!=0){
            modeloXCitaRepository.fillcuest(idmc);
            return "redirect:/paciente/formCuestionario?idcuest="+idCuestionario.toString()+"&idcita="+idcitatoreply;
        }
        else {
            if (estado == 6){

                String especialidadespend =  paciente.getEspecialidadesPendientes();
                List<Integer> idList = Arrays.stream(especialidadespend.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                System.out.println(idList);
                List<Especialidad> listaEspecialidadExPend = especialidadRepository.findAllById(idList);
                model.addAttribute("especialidadesPendExam", listaEspecialidadExPend);

            }
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

    }

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "**")
    public RedirectView redirectToDelivery() {
        return new RedirectView("/paciente/");
    }

    @GetMapping(value = "/delivery")
    public String delivery(Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        model.addAttribute("pacientelog",paciente);
        List<Cita> citasconenvio = citaRepository.citasDelivery(paciente.getIdpaciente());
        model.addAttribute("citas",citasconenvio);
        return "paciente/Deliverytrack";
    }


    @GetMapping(value = "/deliverytrack")
    public String deliverytrack(@RequestParam("idcita") String idcitastr,Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        try {
            Integer idcita = Integer.parseInt(idcitastr);
            List<Cita> citasconenvio = citaRepository.citasDelivery(paciente.getIdpaciente());

            for(Cita c: citasconenvio){
                System.out.println("cita id: " + c.getIdcita());
                System.out.println("cita enviada por utrl: " + idcita);
                System.out.println("paciente y cita?: " + c.getPaciente().getIdpaciente().toString().equals(paciente.getIdpaciente().toString()));
                System.out.println("citas y cita enviada : " +c.getIdcita().toString().equals(idcitastr));
                if(c.getPaciente().getIdpaciente().toString().equals(paciente.getIdpaciente().toString()) && c.getIdcita().toString().equals(idcitastr)){
                    model.addAttribute("pacientelog",paciente);
                    model.addAttribute("receta",recetaMedicaRepository.recetaCita(c.getIdcita()));
                    model.addAttribute("citas",citasconenvio);
                    return "paciente/Deliverytrackview";
                }
            }
        }catch (NumberFormatException e){
            System.out.println("cualquir hvs");
            return "redirect:/paciente/delivery";
        }
        return "redirect:/paciente/delivery";
    }

    @GetMapping(value = "/perfilDoctor")
    public String perfilDoc(RedirectAttributes redirectAttributes, @RequestParam("iddoc") String iddoc, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        model.addAttribute("pacientelog",paciente);
        try {
            Integer idc = Integer.parseInt(iddoc);
            Optional<Doctor> optionalDoctor = doctorRepository.findById(idc);
            if(optionalDoctor.isPresent()){
                Doctor doctor=optionalDoctor.get();
                model.addAttribute("doc",doctor);
                if(eventocalendariodoctorRepository.getDiasProx1(doctor.getIddoctor()).size()>=1){
                    model.addAttribute("dias1",eventocalendariodoctorRepository.getDiasProx1(doctor.getIddoctor()));
                }
                return "paciente/perfilDoctor";
            }
            else {
                return "redirect:/paciente/";
            }
        }catch (NumberFormatException e){
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
    public String selectDate(Model model, @RequestParam(value = "per", required = false) Integer perfil, @RequestParam("iddoc") Integer id, @RequestParam("semana") Integer semana, RedirectAttributes redirectAttributes){


        //para el brecrumb
        if (perfil != null){
            model.addAttribute("per", 1);
        } else {
            model.addAttribute("per", 0);
        }
        //---------------------------
        Doctor doc = doctorRepository.buscarDoctorPorId(id);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        model.addAttribute("doc", doc);
        model.addAttribute("tipocita",tipoCitaRepository.findAll());

        int semana_equivocada = 0;


        if (semana == 0){

            model.addAttribute("prev_semana", semana);
            model.addAttribute("lunes",eventocalendariodoctorRepository.listaLunes(doc.getIddoctor()));
            model.addAttribute("martes",eventocalendariodoctorRepository.listaMartes(id));
            model.addAttribute("miercoles",eventocalendariodoctorRepository.listaMiercoles(id));
            model.addAttribute("jueves",eventocalendariodoctorRepository.listaJueves(id));
            model.addAttribute("viernes",eventocalendariodoctorRepository.listaViernes(id));
            model.addAttribute("sabado",eventocalendariodoctorRepository.listaSabados(id));
        } else if (semana == 1) {

            model.addAttribute("prev_semana", semana);
            model.addAttribute("lunes",eventocalendariodoctorRepository.listaLunes1(doc.getIddoctor()));
            model.addAttribute("martes",eventocalendariodoctorRepository.listaMartes1(id));
            model.addAttribute("miercoles",eventocalendariodoctorRepository.listaMiercoles1(id));
            model.addAttribute("jueves",eventocalendariodoctorRepository.listaJueves1(id));
            model.addAttribute("viernes",eventocalendariodoctorRepository.listaViernes1(id));
            model.addAttribute("sabado",eventocalendariodoctorRepository.listaSabados1(id));
        } else if (semana == 2) {

            model.addAttribute("prev_semana", semana);
            model.addAttribute("lunes",eventocalendariodoctorRepository.listaLunes2(doc.getIddoctor()));
            model.addAttribute("martes",eventocalendariodoctorRepository.listaMartes2(id));
            model.addAttribute("miercoles",eventocalendariodoctorRepository.listaMiercoles2(id));
            model.addAttribute("jueves",eventocalendariodoctorRepository.listaJueves2(id));
            model.addAttribute("viernes",eventocalendariodoctorRepository.listaViernes2(id));
            model.addAttribute("sabado",eventocalendariodoctorRepository.listaSabados2(id));

        } else if (semana == 3) {

            model.addAttribute("prev_semana", semana);
            model.addAttribute("lunes",eventocalendariodoctorRepository.listaLunes3(doc.getIddoctor()));
            model.addAttribute("martes",eventocalendariodoctorRepository.listaMartes3(id));
            model.addAttribute("miercoles",eventocalendariodoctorRepository.listaMiercoles3(id));
            model.addAttribute("jueves",eventocalendariodoctorRepository.listaJueves3(id));
            model.addAttribute("viernes",eventocalendariodoctorRepository.listaViernes3(id));
            model.addAttribute("sabado",eventocalendariodoctorRepository.listaSabados3(id));


        } else if (semana == 4) {

            model.addAttribute("prev_semana", semana);
            model.addAttribute("lunes",eventocalendariodoctorRepository.listaLunes4(doc.getIddoctor()));
            model.addAttribute("martes",eventocalendariodoctorRepository.listaMartes4(id));
            model.addAttribute("miercoles",eventocalendariodoctorRepository.listaMiercoles4(id));
            model.addAttribute("jueves",eventocalendariodoctorRepository.listaJueves4(id));
            model.addAttribute("viernes",eventocalendariodoctorRepository.listaViernes4(id));
            model.addAttribute("sabado",eventocalendariodoctorRepository.listaSabados4(id));
        } else if ( semana > 4  || semana < 0){

            semana = 0;
            semana_equivocada = 1;


            model.addAttribute("prev_semana", semana);
            model.addAttribute("lunes",eventocalendariodoctorRepository.listaLunes(doc.getIddoctor()));
            model.addAttribute("martes",eventocalendariodoctorRepository.listaMartes(id));
            model.addAttribute("miercoles",eventocalendariodoctorRepository.listaMiercoles(id));
            model.addAttribute("jueves",eventocalendariodoctorRepository.listaJueves(id));
            model.addAttribute("viernes",eventocalendariodoctorRepository.listaViernes(id));
            model.addAttribute("sabado",eventocalendariodoctorRepository.listaSabados(id));

        }

        if(semana_equivocada == 1) {

            redirectAttributes.addFlashAttribute("msg2", "Solo puede reservar citas para un mes como máximo");

        }


        model.addAttribute("diainicioSemana", eventocalendariodoctorRepository.obtnerdiaInicioSemana(semana));
        model.addAttribute("mesinicioSemana", eventocalendariodoctorRepository.obtnermesInicioSemana(semana));
        model.addAttribute("anoinicioSemana", eventocalendariodoctorRepository.obtneranoInicioSemana(semana));


        model.addAttribute("diafinSemana", eventocalendariodoctorRepository.obtenerdiaFinSemana(semana));
        model.addAttribute("mesfinSemana", eventocalendariodoctorRepository.obtnermesFinSemana(semana));
        model.addAttribute("anofinSemana", eventocalendariodoctorRepository.obtneranoFinSemana(semana));



        model.addAttribute("nombre_mes", eventocalendariodoctorRepository.obtenerMes(semana));

        model.addAttribute("pacientelog",paciente);
        //Eventocalendariodoctor eventocalendariodoctor = eventocalendariodoctorRepository.calendarioPorDoctor(id);
        model.addAttribute("calendario", eventocalendariodoctorRepository.calendarioPorDoctor(id));
        return "paciente/reservar2";
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
    @GetMapping(value = "/agendarCita_Sede")
    public String agendarCita2(Model model){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        List<Sede> listsede = sedeRepository.findAll();
        model.addAttribute("sedesparacitas", listsede);

        return "paciente/agendarCita_Sede";
    }
    @GetMapping(value = "/agendarCita_Esp")
    public String agendarCita3(Model model, @RequestParam("idsede") String  idsedestr) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        try{
            Integer idsede = Integer.parseInt(idsedestr);
            Sede sede = sedeRepository.findByIdsede(idsede);
            if(sedeRepository.findById(idsede).isPresent()){
                model.addAttribute("sede", sede);
                List<Especialidad> listespecialidad = especialidadRepository.listaEspxSede(idsede);
                model.addAttribute("especialidades", listespecialidad);
                return "paciente/agendarCita_Esp";
            }else {
                return "redirect:/paciente/agendarCita_Sede";
            }

        }catch (NumberFormatException e){
            return "redirect:/paciente/agendarCita_Sede";
        }
    }
    @GetMapping(value = "/agendarCita_Doc")
    public String agendarCita4(Model model, @RequestParam("idsede") String idsedesrt, @RequestParam("idesp") String idespstr){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        try {
            Integer idsede = Integer.parseInt(idsedesrt);
            Integer idesp = Integer.parseInt(idespstr);
            if(sedeRepository.findById(idsede).isPresent() && especialidadRepository.findById(idesp).isPresent()){
                Sede sede = sedeRepository.findByIdsede(idsede);
                Especialidad especialidad = especialidadRepository.findByIdespecialidad(idesp);
                model.addAttribute("sede", sede);
                model.addAttribute("especialidad", especialidad);
                List<Doctor> listdoctor = doctorRepository.DoctorxEspxSede(idsede, idesp);
                model.addAttribute("doctores", listdoctor);
                return "paciente/agendarCita_Doc";
            }else {
                return "redirect:/paciente/agendarCita_Sede";
            }
        }catch (NumberFormatException e){
            return "redirect:/paciente/agendarCita_Sede";
        }
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
    @GetMapping(value = "/calendarioSemanal")
    public String calendarioMensual(Model model, @RequestParam("semana") String semanastr, RedirectAttributes redirectAttributes){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        try {
            Integer semana = Integer.parseInt(semanastr);
            int id=paciente.getIdpaciente();
            int semana_equivocada = 0;
            if (semana == 0){
                model.addAttribute("prev_semana", semana);
                model.addAttribute("lunes",citaRepository.listaLunes(id));
                model.addAttribute("martes",citaRepository.listaMartes(id));
                model.addAttribute("miercoles",citaRepository.listaMiercoles(id));
                model.addAttribute("jueves",citaRepository.listaJueves(id));
                model.addAttribute("viernes",citaRepository.listaViernes(id));
                model.addAttribute("sabado",citaRepository.listaSabados(id));
            } else if (semana == 1) {

                model.addAttribute("prev_semana", semana);
                model.addAttribute("lunes",citaRepository.listaLunescitas1(id));
                model.addAttribute("martes",citaRepository.listaMartescitas1(id));
                model.addAttribute("miercoles",citaRepository.listaMiercolescitas1(id));
                model.addAttribute("jueves",citaRepository.listaJuevescitas1(id));
                model.addAttribute("viernes",citaRepository.listaViernescitas1(id));
                model.addAttribute("sabado",citaRepository.listaSabadoscitas1(id));
            } else if (semana == 2) {

                model.addAttribute("prev_semana", semana);
                model.addAttribute("lunes",citaRepository.listaLunescita2(id));
                model.addAttribute("martes",citaRepository.listaMartescitas2(id));
                model.addAttribute("miercoles",citaRepository.listaMiercolescitas2(id));
                model.addAttribute("jueves",citaRepository.listaJuevescitas2(id));
                model.addAttribute("viernes",citaRepository.listaViernescitas2(id));
                model.addAttribute("sabado",citaRepository.listaSabadoscitas2(id));

            } else if (semana == 3) {

                model.addAttribute("prev_semana", semana);
                model.addAttribute("lunes",citaRepository.listaLunescitas3(id));
                model.addAttribute("martes",citaRepository.listaMartescitas3(id));
                model.addAttribute("miercoles",citaRepository.listaMiercolescitas3(id));
                model.addAttribute("jueves",citaRepository.listaJuevescitas3(id));
                model.addAttribute("viernes",citaRepository.listaViernescitas3(id));
                model.addAttribute("sabado",citaRepository.listaSabadoscitas3(id));


            } else if (semana == 4) {

                model.addAttribute("prev_semana", semana);
                model.addAttribute("lunes",citaRepository.listaLunescitas4(id));
                model.addAttribute("martes",citaRepository.listaMartescitas4(id));
                model.addAttribute("miercoles",citaRepository.listaMiercolescitas4(id));
                model.addAttribute("jueves",citaRepository.listaJuevescitas4(id));
                model.addAttribute("viernes",citaRepository.listaViernescitas4(id));
                model.addAttribute("sabado",citaRepository.listaSabadoscitas4(id));
            } else if ( semana > 4  || semana < 0){

                semana = 0;
                semana_equivocada = 1;
                model.addAttribute("prev_semana", semana);
                model.addAttribute("lunes",citaRepository.listaLunes(id));
                model.addAttribute("martes",citaRepository.listaMartes(id));
                model.addAttribute("miercoles",citaRepository.listaMiercoles(id));
                model.addAttribute("jueves",citaRepository.listaJueves(id));
                model.addAttribute("viernes",citaRepository.listaViernes(id));
                model.addAttribute("sabado",citaRepository.listaSabados(id));

            }
            if(semana_equivocada == 1){
                redirectAttributes.addFlashAttribute("msg2","Solo puede reservar citas para un mes como máximo");
            }
            model.addAttribute("diainicioSemana", citaRepository.obtnerdiaInicioSemana(semana));
            model.addAttribute("mesinicioSemana", citaRepository.obtnermesInicioSemana(semana));
            model.addAttribute("anoinicioSemana", citaRepository.obtneranoInicioSemana(semana));
            model.addAttribute("diafinSemana", citaRepository.obtenerdiaFinSemana(semana));
            model.addAttribute("mesfinSemana", citaRepository.obtnermesFinSemana(semana));
            model.addAttribute("anofinSemana", citaRepository.obtneranoFinSemana(semana));
            model.addAttribute("nombre_mes", citaRepository.obtenerMescita(semana));
            model.addAttribute("pacientelog",paciente);
            return "paciente/calendarioSemanal";
        }catch (NumberFormatException e){
            return "redirect:paciente/calendarioSemanal?semana=0";
        }
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


    @GetMapping(value = "/cuestionarios")
    public String cuestionarios(@RequestParam(value = "mensaje_url", required = false) String mensaje_url,Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        int id_user = usuario.getIdusuario();
        int id_tipo_user = usuario.getTipodeusuario().getIdtipodeusuario();

//        System.out.println(id_user);
//        System.out.println(id_tipo_user);

        System.out.println("llega a lista cuestionarios");



        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

//        System.out.println(paciente);

        List<Cita> listaCitas = citaRepository.citasxUsuario(paciente.getIdpaciente());



        List<Cita> listaCita1 = new ArrayList<>();

        List<Cita> listaCita2 = new ArrayList<>();

//        System.out.println(listaCitas);


//        List<ModeloPorCita> listaModelosxCita = modeloJsonRepository.consultarModelo(cita_unica.getIdcita());
        List<ModeloJsonEntity> listamodelos = new ArrayList<>();

        List<DatosJsonEntity> listamodelos_datosllenos = new ArrayList<>();

        int id_cita = 0;
        List<Cita> citas = new ArrayList<>();
        for(Cita cita_unica: listaCitas){
//            System.out.println(cita_unica);
            Integer id_modelo = modeloJsonRepository.consultarModelo(cita_unica.getIdcita());
//            System.out.println(id_modelo);

            //validar si existe un cuestionario asignado a una cita
            if (id_modelo != null){



                //validar si existe un cuestionario lleno para colocarlo en la parte de historico
                Integer id_datos_llenos = datosJsonRepository.buscarsiexisteRegistro(cita_unica.getIdcita());

                //si en caso no hay coincidencias
                if(id_datos_llenos == null) {




//                    System.out.println(id_modelo);

                    ModeloJsonEntity modelo_cuestionario_2 = modeloJsonRepository.Cuestionario(id_modelo);

//                    ModeloJsonEntity modelo_cuestionario_2 = modeloJsonRepository.listaCuestionarios_2(id_modelo,cita_unica.getIdcita());


                    if (modelo_cuestionario_2 != null) {
                        System.out.println(modelo_cuestionario_2.getNombrePlantilla());
                        listamodelos.add(modelo_cuestionario_2);
                        listaCita1.add(cita_unica);
                    }

                }else {




                    //si en caso hay coincidencias
                    DatosJsonEntity datos_json_cuestionario_2 = datosJsonRepository.DatosLlenos(id_datos_llenos);


                    if (datos_json_cuestionario_2 != null) {
                        listamodelos_datosllenos.add(datos_json_cuestionario_2);
                        listaCita2.add(cita_unica);
//                        System.out.println(datos_json_cuestionario_2);
                    }
                }






//                if(datos_json_cuestionario_2 != null) {
//                    System.out.println(datos_json_cuestionario_2);
//                    listamodelos_datosllenos.add(datos_json_cuestionario_2);
//                }
//
//                listamodelos.add(modelo_cuestionario_2);






            }

        }



        model.addAttribute("listacita1",listaCita1);
        model.addAttribute("listacita2",listaCita2);



        model.addAttribute("listaidcitas",citas);

        model.addAttribute("list_cuestionario_2",listamodelos);

//        model.addAttribute("lista_cuest_llenos",lista_cuest_llenos);


        model.addAttribute("list_cuestionario_3",listamodelos_datosllenos);


        model.addAttribute("pacientelog",paciente);


//        String mensaje = (String) model.getAttribute("msg");
//        if (mensaje != null) {
//            model.addAttribute("mensaje", mensaje);
//        }


        String mensaje_2 = mensaje_url;
        if (mensaje_2 == "completado") {
            System.out.println(mensaje_2);
            model.addAttribute("mensaje", "Cuestionario Completado");
        }




        return "paciente/cuestionariosPaciente";
    }


    //ver cuest
    @GetMapping(value = "/vercuestionario")
    public String vercuestionario(@RequestParam("idDatosJson") Integer idDatosJson,Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");


        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());


        model.addAttribute("pacientelog",paciente);


        model.addAttribute("informelleno",datosJsonRepository.modeloJsonLlenado(idDatosJson));

        return "paciente/CuestionarioLleno";

    }









    @Autowired
    private  ModeloJsonRepository modeloJsonRepository;
    @GetMapping(value = "/formCuestionario")
    public String formCuestinario(@RequestParam("idcuest") String idstr,@RequestParam("idcita") int idcita,Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

//        ModeloJsonEntity modelo_cuestionario_2 = modeloJsonRepository.listaCuestionarios(idcuest);

        try {
            Integer id = Integer.parseInt(idstr);
//            Optional<DatosJsonEntity> optional = datosJsonRepository.findById(id);
//            if(optional.isPresent()){
//                DatosJsonEntity datos = optional.get();
//                model.addAttribute("datos",datos);
            model.addAttribute("pacientelog",paciente);
            model.addAttribute("id_cuest",id);
            model.addAttribute("idcita",idcita);
//                int cuestionarioMedicoId = modeloJsonRepository.cuestionarioMedicoId(datos.getCita().getEspecialidad().getIdespecialidad());
            model.addAttribute("listapreguntascuestionario",modeloJsonRepository.listarPreguntasxPlantilla(id));
            return "paciente/formCuestionario";
//            }
//            else {
//                return "redirect:/paciente/cuestionarios";
//            }
        }
        catch (NumberFormatException e){
            return "redirect:/paciente/cuestionarios";
        }
    }

    //    @ResponseBody
    @PostMapping(value = "/llenarCuestionario")
    @Transactional
    public String llenarCuestionario(RedirectAttributes redirectAttributes,@RequestParam("valores") List<String> valores){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        System.out.println("llega al repo de llenar");
//        System.out.println(valores);
        int id_usuario = usuario.getIdusuario();
        String primerValor_id = valores.get(0);
        String segValor_id = valores.get(1);
//        System.out.println(primerValor_id);
        valores.remove(0);
        valores.remove(0);
//        System.out.println(valores);
        int primerValorInt_id = Integer.parseInt(String.valueOf(primerValor_id));
        int primerValorInt_id_cita = Integer.parseInt(String.valueOf(segValor_id));
        ModeloJsonEntity EncontrarModelo = modeloJsonRepository.buscarModeloEdit(primerValorInt_id);
        String nbr_plantilla = EncontrarModelo.getNombrePlantilla();
        int id_especialidad = EncontrarModelo.getEspecialidad().getIdespecialidad();
        int id_tipo_usuario = EncontrarModelo.getTipodeusuario().getIdtipodeusuario();
        Byte flg_formulario = EncontrarModelo.getFormulario();
        Byte flg_cuestionario = EncontrarModelo.getCuestionario();
        Byte flg_informe = EncontrarModelo.getInforme();

//        System.out.println("nbr_plantilla="+nbr_plantilla);
//        System.out.println("id_especialidad="+id_especialidad);
//        System.out.println("id_tipo_usuario="+id_tipo_usuario);
//        System.out.println("flg_formulario="+flg_formulario);
//        System.out.println("flg_cuestionario="+flg_cuestionario);
//        System.out.println("flg_informe="+flg_informe);


        for (String elemento : valores) {
            tablaDatosLlenosRepository.agregarDatosDeInput(elemento);
//            System.out.println(elemento);
        }


        int id_registro_nuevo = 0;
        id_registro_nuevo = datosJsonRepository.contarRegistros();
        tablaDatosLlenosRepository.LlenadoDePlantilla(primerValorInt_id,nbr_plantilla,id_usuario,primerValorInt_id,primerValorInt_id_cita);


        //BORRADO DE LA TABLA DE TITULOS.

        //jalar para borrar
        tablaDatosLlenosRepository.BorrarDatosDeInput();


        redirectAttributes.addFlashAttribute("msg", "Cuestionario Completado");
        return "redirect:/paciente/cuestionarios";
//        return "hola";

    }

    @PostMapping(value = "/respuestas")
    public String respuestasCuestionarios(HttpServletRequest request,@RequestParam("idcuest") String idstr){
        Enumeration<String> parameterNames = request.getParameterNames();
        Integer id = Integer.parseInt(idstr);
        Optional<DatosJsonEntity> optional = datosJsonRepository.findById(id);
        DatosJsonEntity datos = optional.get();

        int cuestionarioMedicoId = modeloJsonRepository.cuestionarioMedicoId(datos.getCita().getEspecialidad().getIdespecialidad());
        for(String k :   modeloJsonRepository.listarPreguntasxPlantilla(cuestionarioMedicoId)){
            System.out.println(k);
        }


        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.startsWith("respuesta")) {
                String respuesta = request.getParameter(parameterName);
                System.out.println(respuesta);
            }
        }
        return "paciente/pruebas";
    }
    @GetMapping(value ="/receta")
    public String receta(@RequestParam("idcita") String idstr, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        try {
            Integer id = Integer.parseInt(idstr);
            Optional<Cita> optional = citaRepository.findById(id);
            if(optional.isPresent()){
                Cita cita = optional.get();
                List<RecetaMedica> listarecetaMedica = recetaMedicaRepository.recetaMedicaPorCita(cita.getIdcita());
                model.addAttribute("cita",cita);
                model.addAttribute("recetas",listarecetaMedica);
                model.addAttribute("pacientelog",paciente);

                return "paciente/recetasPaciente";
            }
            else {
                return "redirect:/paciente/historialCitas";
            }
        }
        catch (NumberFormatException e){
            return "redirect:/paciente/historialCitas";
        }
    }
    @GetMapping(value ="/boleta")
    public String boletas(@RequestParam("idcita") String idstr, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        try {
            Integer id = Integer.parseInt(idstr);
            Optional<Cita> optional = citaRepository.findById(id);
            if(optional.isPresent()){
                Cita cita = optional.get();
                BoletaPaciente boletaPaciente = boletaPacienteRepository.getBoletaCita(cita.getIdcita());
                model.addAttribute("boletapac",boletaPaciente);
                model.addAttribute("pacientelog",paciente);
                return "paciente/boletaCita";
            }
            else {
                return "redirect:/paciente/historialCitas";
            }
        }
        catch (NumberFormatException e){
            return "redirect:/paciente/historialCitas";
        }
    }

    @GetMapping(value = "/notificaciones")
    public String notif(Model model) {
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        model.addAttribute("notificaciones",notificacionesRepository.notificacionesPorUsuario(usuario.getIdusuario()));
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

    @PostMapping(value="/eliminaralergia")
    @Transactional
    public String eliminarAlergia(@RequestParam("alergia") String alergia, @RequestParam("idpaciente2") Integer id, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        // Obtener el primer carácter de la cadena "alergia"
        Integer idx = Integer.parseInt(alergia.substring(0, 1));
        System.out.println(idx);
        System.out.println(id);

        pacienteRepository.borrarAlergia(idx, id);
        redirectAttributes.addFlashAttribute("msg2","Alergia borrada correctamente");
        return "redirect:/paciente/perfil"; // Ruta a la que se redirigirá después de eliminar la alergia
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
    @PostMapping(value = "/editarperfil")
    @Transactional
    public String editarperfil(@RequestParam("idusuario") int idUsuario,
                               @RequestParam("direccion") String direccion,
                               @RequestParam("telefono") int telefono, RedirectAttributes redirectAttributes ){
        usuarioRepository.actualizarPerfilPaciente(telefono, idUsuario);
        pacienteRepository.actualizarPaciente(direccion, idUsuario);

        redirectAttributes.addFlashAttribute("psw1", "Perfil Actualizado");



        return "redirect:/paciente/perfil";
    }

    @PostMapping(value = "/pruebascita")
    @Transactional
    public String pruebascita(@RequestParam("idsede") Integer idsede, @RequestParam("especialidadid") Integer idesp,
                              @RequestParam("iddoctor") Integer iddoctor, @RequestParam("fecha") LocalDate fecha ,
                              @RequestParam("hora")LocalTime hora, @RequestParam("idseguro") Integer idseguro,
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
                        Cita citaAgendada = citaRepository.citaAgendada(fecha,hora,iddoctor);
                        Doctor doctor = doctorRepository.buscarDoctorPorId(iddoctor);
                        eventocalendariodoctorRepository.cambiarEstadoCalendario(iddoctor,fecha,hora);
                        String content = "Usted reservó una cita para "+ fecha+ " en la siguiente hora: " + hora + " En la especialiad de " + especialidadRepository.findById(idesp).get().getNombre() + ".";
                        String titulo = "Cita reservada con exito";
                        String content2 = "Estimado doctor, tiene una cita programada para el "+ fecha+ " en la siguiente hora: " + hora +" con el paciente : "+paciente.getUsuario().getNombres()+" "+paciente.getUsuario().getApellidos()+ "";
                        String titulo2= "Cita Programa para " +fecha+ "";
                        notificacionesRepository.notificarcita(usuario.getIdusuario(),content,titulo);
                        pacienteRepository.actualizarEstadoPaciente(4,paciente.getIdpaciente());
                        notificacionesRepository.notificarcita(doctor.getUsuario().getIdusuario(),content2,titulo2);
                        if(idtipocita==1){
                            boletaDoctorRepository.generarBoletaDoctorCita(citaAgendada.getIdcita(),paciente.getIdpaciente(),idseguro,iddoctor,montoDoctor);
                            boletaPacienteRepository.generarBoletaPacienteCita(paciente.getIdpaciente(),citaAgendada.getIdcita(),idseguro,montoPaciente);
                            emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita para el "+fecha.toString()+ ".\n"+"En la sede "+sedeRepository.findById(idsede).get().getNombre()+" ubicada " +sedeRepository.findById(idsede).get().getDireccion());
                        }else{

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            String fechaFormateada = sdf.format(fecha);
                            System.out.println("La fecha es:"+ fechaFormateada);
                            emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita virtual para el "+fechaFormateada+ ".\n"+"El link para la sesion de zoom es el siguiente: " + doctorRepository.findById(iddoctor).get().getZoom());

                            //emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita virtual para el "+fecha.toString()+ ".\n"+"El link para la sesion de zoom es el siguiente: " + doctorRepository.findById(iddoctor).get().getZoom());
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
    @GetMapping(value = "/pagos")
    public String pagosView(Model model){
        //Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        //Paciente paciente =  optionalPaciente.get();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        model.addAttribute("pacientelog",paciente);
        if(citaRepository.paymentcitas(paciente.getIdpaciente()).size()>=1){
            List<Double> costoscita = new ArrayList<>();
            for(Cita c : citaRepository.paymentcitas(paciente.getIdpaciente())){
                DecimalFormat df = new DecimalFormat("#.##");
                String resultadoFormateado = df.format(c.getEspecialidad().getCosto()*paciente.getSeguro().getCoaseguro());
                costoscita.add(Double.parseDouble(resultadoFormateado));
            }
            List<Cita> citasTopay = citaRepository.paymentcitas(paciente.getIdpaciente());
            System.out.printf("id log: "+ paciente.getIdpaciente());
            model.addAttribute("costo",costoscita);
            model.addAttribute("pagos",citasTopay);
        }
        return "paciente/pagos";
    }

    @GetMapping(value = "/pagar")
    public String pagar(@RequestParam("idcita") String idcita, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        try {
            Integer id = Integer.parseInt(idcita);
            Optional citaopt = citaRepository.findById(id);
            if(citaopt.isPresent()){
                Cita cita = citaRepository.findById(id).get();
                if(cita.getPaciente().getIdpaciente()==paciente.getIdpaciente() && cita.getEstadoCita().getIdestadocita()==1){
                    DecimalFormat df = new DecimalFormat("#.##");
                    String resultadoFormateado = df.format(cita.getEspecialidad().getCosto()*paciente.getSeguro().getCoaseguro());
                    Double monto = Double.parseDouble(resultadoFormateado);
                    model.addAttribute("pacientelog",paciente);
                    model.addAttribute("cita",cita);
                    model.addAttribute("monto",monto);
                    return "paciente/checkout";
                }else {
                    return "redirect:/paciente/pagos";
                }
            }else {
                return "redirect:/paciente/pagos";
            }
        }catch (NumberFormatException e){
            return "redirect:/paciente/pagos";
        }

    }
    @Autowired TarjetasRepository tarjetasRepository;
    @PostMapping(value = "/checkoutpayment")
    @Transactional
    public String checkout(@RequestParam("cardnumber") String cardnumber,
                           @RequestParam("month") String month,
                           @RequestParam("year") String year,
                           @RequestParam("cvv") String cvv,
                           @RequestParam("idcita") String idcita, RedirectAttributes redirectAttributes){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        try {
            Integer idtopay = Integer.parseInt(idcita);


            String montopac = getmontopac(idtopay);
            Float montoDoctor = getmontoDoc(idtopay);
            if(citaRepository.findById(idtopay).isPresent()){
                Cita citaAgendada = citaRepository.findById(idtopay).get();
                Doctor doc = citaAgendada.getDoctor();
                Boolean aprove = false;

                if(citaAgendada.getPaciente().getIdpaciente()==paciente.getIdpaciente() && citaAgendada.getEstadoCita().getIdestadocita()==1){
                    for (Tarjetas t: tarjetasRepository.findAll()){
                        if (t.getNumero().equals(cardnumber)){
                            if (t.getMes().equals(month)){
                                if (t.getAnio().equals(year)){
                                    if(t.getCvv().equals(cvv)){
                                        aprove = true;
                                    }
                                }
                            }
                        }
                    }
                    if(aprove){
                        boletaDoctorRepository.generarBoletaDoctorCita(citaAgendada.getIdcita(),paciente.getIdpaciente(),paciente.getSeguro().getIdseguro(),doc.getIddoctor(),montoDoctor);
                        boletaPacienteRepository.generarBoletaPacienteCita(paciente.getIdpaciente(),citaAgendada.getIdcita(),paciente.getSeguro().getIdseguro(),Float.parseFloat(montopac));
                        citaRepository.actualizarEstadoCita(2,idtopay);
                        return "redirect:/paciente/boleta?idcita="+idtopay.toString();
                    }
                    else {
                        redirectAttributes.addFlashAttribute("fail","Datos incorrectos");
                        return "redirect:/paciente/pagar?idcita="+idtopay.toString();
                    }
                }
                else {
                    return "redirect:/paciente/pagos";
                }
            }else {
                return "redirect:/paciente/pagos";
            }
        }catch (NumberFormatException e){
            return "redirect:/paciente/pagos";
        }
    }

    public Float getmontoDoc(Integer idcita){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        Cita citaAgendada = citaRepository.findById(idcita).get();
        Doctor doc = citaAgendada.getDoctor();
        Double costoEspecialidad = especialidadRepository.getCosto(doc.getEspecialidad().getIdespecialidad());
        Double comisionDoctor = seguroRepository.getCosto(paciente.getSeguro().getIdseguro());
        Float montoDoctor = (float) (costoEspecialidad * comisionDoctor);
        return montoDoctor;
    }

    public String getmontopac(Integer idcita) {
        Cita cita = citaRepository.findById(idcita).get();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        DecimalFormat df = new DecimalFormat("#.##");
        String resultadoFormateado = df.format(cita.getEspecialidad().getCosto() * paciente.getSeguro().getCoaseguro());
        return resultadoFormateado;
    }

    @PostMapping(value = "/pagospruebas")
    public String pagosPr(@RequestParam(value = "citapagos", required = false) Integer []id, RedirectAttributes redirectAttributes){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());
        System.out.println("ids:  " + id.length);
        if(id.length>0){
            List<Cita> lisaCitas = new ArrayList<>();
            Double costos = 0.0;
            for(int i=0;i<id.length;i++){
                lisaCitas.add(citaRepository.findById(id[i]).get());
                DecimalFormat df = new DecimalFormat("#.##");
                String resultadoFormateado = df.format(lisaCitas.get(i).getEspecialidad().getCosto()*paciente.getSeguro().getCoaseguro());
                costos=costos+Double.parseDouble(resultadoFormateado);
            }
            redirectAttributes.addFlashAttribute("msj",costos);
        }
        return "redirect:/paciente/pagos";
    }

    @PostMapping(value = "/reservar2")
    @Transactional
    public String reserva2 (@RequestParam("idev") Integer idev, @RequestParam("idtipocita") Integer idtipocita, RedirectAttributes redirectAttributes) throws Exception {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Paciente paciente = pacienteRepository.pacXuser(usuario.getIdusuario());

        if(eventocalendariodoctorRepository.findById(idev).isPresent()){
            Eventocalendariodoctor eventocalendariodoctor = eventocalendariodoctorRepository.findById(idev).get();
            Doctor doc = doctorRepository.findById(eventocalendariodoctor.getDoctor().getIddoctor()).get();
            // que no sea cita repetida
            List<Cita> citarep = citaRepository.finddouble(paciente.getIdpaciente(),eventocalendariodoctor.getFecha(), eventocalendariodoctor.getHorainicio());
            System.out.println("arr len: "+ citarep.size());
            if(citarep.size()==0){
                citaRepository.agengedarcita(doc.getSede().getIdsede(),
                        doc.getEspecialidad().getIdespecialidad(),
                        eventocalendariodoctor.getFecha(),
                        eventocalendariodoctor.getHorainicio(),
                        eventocalendariodoctor.getHorainicio().plusHours(1),
                        1,
                        idtipocita,
                        paciente.getSeguro().getIdseguro(),
                        1,
                        paciente.getIdpaciente(),
                        doc.getIddoctor());
                eventocalendariodoctorRepository.cambiarEstadoCalendario(doc.getIddoctor(),
                        eventocalendariodoctor.getFecha(),
                        eventocalendariodoctor.getHorainicio());
                Double costoEspecialidad = especialidadRepository.getCosto(doc.getEspecialidad().getIdespecialidad());
                Double comisionDoctor = seguroRepository.getCosto(paciente.getSeguro().getIdseguro());
                Double coaseguroPaciente = seguroRepository.getCoaseguro(paciente.getSeguro().getIdseguro());
                Float montoDoctor = (float) (costoEspecialidad * comisionDoctor);
                Float montoPaciente = (float) (costoEspecialidad * coaseguroPaciente);
                Cita citaAgendada = citaRepository.citaAgendada(eventocalendariodoctor.getFecha(),eventocalendariodoctor.getHorainicio(),doc.getIddoctor());
                String content = "Usted reservó una cita para "+ eventocalendariodoctor.getFecha()+ "en la siguiente hora: " + eventocalendariodoctor.getHorainicio() + " En la especialiad de " + doc.getEspecialidad().getNombre() + ".";
                String titulo = "Cita reservada con exito";
                notificacionesRepository.notificarcita(usuario.getIdusuario(),content,titulo);
                if(idtipocita==1){
                    boletaDoctorRepository.generarBoletaDoctorCita(citaAgendada.getIdcita(),paciente.getIdpaciente(),paciente.getSeguro().getIdseguro(),doc.getIddoctor(),montoDoctor);
                    boletaPacienteRepository.generarBoletaPacienteCita(paciente.getIdpaciente(),citaAgendada.getIdcita(),paciente.getSeguro().getIdseguro(),montoPaciente);
                    /* GMailer enviocorreo = new GMailer();
                    String receiverEmail = usuario.getCorreo(); // Aquí puedes colocar la dirección de correo electrónico del receptor deseado
                    String cntpres ="Estimado usuario usted reservó una cita para el "+eventocalendariodoctor.getFecha().toString()+ ".\n"+"En la sede "+sedeRepository.findById(doc.getSede().getIdsede()).get().getNombre()+" ubicada " +sedeRepository.findById(doc.getSede().getIdsede()).get().getDireccion();
                    enviocorreo.sendMail(titulo,cntpres, receiverEmail);*/


                    Email from = new Email("clinica.lafe.info@gmail.com");
                    String subject = "Confirmación de cita";
                   Email to = new Email(paciente.getUsuario().getCorreo());
                   Content content_2 = new Content("text/plain", "Estimado Paciente, su cita ha sido reservada exitosamente");
                   Mail mail = new Mail(from, subject, to, content_2);

                   SendGrid sg = new SendGrid("");  //aca va el cambio por wsp poner esto
                   Request request = new Request();
                    try {
                        request.setMethod(Method.POST);
                        request.setEndpoint("mail/send");
                        request.setBody(mail.build());
                        Response response = sg.api(request);
                        System.out.println(response.getStatusCode());
                        System.out.println(response.getBody());
                        System.out.println(response.getHeaders());
                    } catch (IOException ex) {
                        throw ex;
                    }



                    emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita","Estimado usuario usted reservó una cita para el "+eventocalendariodoctor.getFecha().toString()+ ".\n"+"En la sede "+sedeRepository.findById(doc.getSede().getIdsede()).get().getNombre()+" ubicada " +sedeRepository.findById(doc.getSede().getIdsede()).get().getDireccion());

                }else{
                    // GMailer enviocorreo = new GMailer();
                    //String receiverEmail = usuario.getCorreo(); // Aquí puedes colocar la dirección de correo electrónico del receptor deseado
                    //enviocorreo.sendMail("Confirmación de cita remoto","Estimado usuario usted reservó una cita virtual para el "+eventocalendariodoctor.getFecha().toString()+ ".\n"+"El link para la sesion de zoom es el siguiente: " +doc.getZoom(), receiverEmail);
                    emailService.sendEmail(paciente.getUsuario().getCorreo(),"Confirmación de cita virtual","Estimado usuario usted reservó una cita virtual para el "+eventocalendariodoctor.getFecha().toString());
                }
                redirectAttributes.addFlashAttribute("msg1", "Ha reservado una cita con éxito");

                return "redirect:/paciente/";
            }else {
                return "redirect:/paciente/";
            }
        }else {
            return "redirect:/paciente/reservar2";
        }
    }




}
