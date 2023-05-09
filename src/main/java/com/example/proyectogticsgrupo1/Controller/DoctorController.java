package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.transaction.Transactional;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/doctor")
public class DoctorController {
    final DoctorRepository doctorRepository;
    final PacienteRepository pacienteRepository;
    final CitaRepository citaRepository;
    final RecetaMedicaRepository recetaMedicaRepository;
    final ReporteCitaRepository reporteCitaRepository;
    final UserRepository userRepository;
    final BitacoraDeDiagnosticoRepository bitacoraDeDiagnosticoRepository;
    final SedeRepository  sedeRepository;

    final CuestionarioRepository cuestionarioRepository;

    final EventocalendariodoctorRepository eventocalendariodoctorRepository;

    final BoletaDoctorRepository boletaDoctorRepository;

    @Autowired
    EmailService emailService;

    public DoctorController(CitaRepository citaRepository, DoctorRepository doctorRepository, PacienteRepository pacienteRepository,
                            RecetaMedicaRepository recetaMedicaRepository, ReporteCitaRepository reporteCitaRepository, UserRepository userRepository,
                            BitacoraDeDiagnosticoRepository bitacoraDeDiagnosticoRepository,
                            EventocalendariodoctorRepository eventocalendariodoctorRepository, CuestionarioRepository cuestionarioRepository, SedeRepository sedeRepository, BoletaDoctorRepository boletaDoctorRepository) {

        this.doctorRepository = doctorRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
        this.recetaMedicaRepository =recetaMedicaRepository;
        this.reporteCitaRepository = reporteCitaRepository;
        this.userRepository = userRepository;
        this.bitacoraDeDiagnosticoRepository= bitacoraDeDiagnosticoRepository;
        this.eventocalendariodoctorRepository = eventocalendariodoctorRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.sedeRepository = sedeRepository;
        this.boletaDoctorRepository = boletaDoctorRepository;
    }


    //Mostrará Por Defecto el Calendario Semanal de Doctor

    @GetMapping("/dashboard")
    public String inicioDashboardDoctor(Model model) {

        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
            List<Cita> citasAgendadas1 = citaRepository.buscarCitasAgendadasDoctor(doctor1.getIddoctor());
            model.addAttribute("citasAgendadas",citasAgendadas1);
            //model.addAttribute("citasAgendadas",citaRepository.buscarCitasAgendadasDoctor(doctor.getIdDoctor()));
            return "doctor/dashboardDoc";
        } else {
            return "redirect:/iniciosesion";
        }

    }

    @GetMapping("/dashboard/diario")
    public String inicioDashboardDoctor2(){


        return "doctor/dashboardDocDiario";
    }

    @GetMapping("/dashboard/mensual")
    public String inicioDashboardDoctor3(){

        return "doctor/dashboardDocMensual";
    }


    @GetMapping("/pacientesatendidos")
    public String pacientesAtendidosDoctor(Model model, @RequestParam("id") int idDoctor) {
        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
            model.addAttribute("pacientesAtendidosDoctor", citaRepository.pacientesAtendidosPorDoctor(idDoctor));

        }
        return "doctor/pacientesAtendidos";
    }

    @GetMapping("/pacientesatendidos/verhistorial")
    public String historialPacienteDoctor(Model model, @RequestParam("id") int idPaciente){
        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
            Paciente paciente1 = pacienteRepository.buscarPacientePorID(idPaciente);
            model.addAttribute("paciente", paciente1);
            model.addAttribute("citaspaciente", citaRepository.citasPorPaciente(idPaciente));
            model.addAttribute("bitacoradiagnostico", bitacoraDeDiagnosticoRepository.bitacoraDeDiagnostico(idPaciente));
        }
            return "doctor/verHistorial";
    }

    @PostMapping("/pacientesatendidos/verhistorial/guardarbitacora")
    @Transactional
    public String guardarBitacora(RedirectAttributes redirectAttributes, @RequestParam("descripcion") String descripcion, @RequestParam("id") int idPaciente){
        bitacoraDeDiagnosticoRepository.guardarbitacora(descripcion,idPaciente);
        redirectAttributes.addAttribute("id",idPaciente);
        return "redirect:/doctor/pacientesatendidos/verhistorial";
    }

    @GetMapping("/pacientesatendidos/verhistorial/borrarbitacora")
    @Transactional
    public String borrarBitacora(RedirectAttributes redirectAttributes, @RequestParam("idB") int idBitacora){
        BitacoraDeDiagnostico bitacora = bitacoraDeDiagnosticoRepository.buscarBitacoraDeDiagnosticoID(idBitacora);
        Integer idPaciente = bitacora.getPaciente().getIdpaciente();
        bitacoraDeDiagnosticoRepository.borrarbitacora(idBitacora);
        redirectAttributes.addAttribute("id",idPaciente);
        return "redirect:/doctor/pacientesatendidos/verhistorial";
    }

    @GetMapping("/pacientesatendidos/verhistorial/boleta")
    public String verBoletaDoctor(Model model, @RequestParam("id") int idCita ){
        BoletaDoctor boletaDoctor = boletaDoctorRepository.buscarBoletaDoctorCita(idCita);
        model.addAttribute("boletadoctor",boletaDoctor);
        return "doctor/boletaDoc";
    }

    @GetMapping("/pacientesatendidos/verhistorial/vercita")
    public String verCitaDoctor(Model model, @RequestParam("id") int idCita,
                                @RequestParam(name="idReceta", defaultValue = "0") int idReceta)
    {
        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
            Cita cita1 = citaRepository.buscarCitaPorId(idCita);
            model.addAttribute("cita", cita1);
            model.addAttribute("recetamedica", recetaMedicaRepository.buscarRecetaMedicaPorCita(idCita, idReceta));
            model.addAttribute("reportecita", reporteCitaRepository.buscarReporteCitaPorId(idCita));
        }
        return "doctor/verCita";
    }
    @GetMapping("/pacientesatendidos/verhistorial/vercita/editarreceta")
    public String verEditarReceta(Model model, @RequestParam("idReceta") int idReceta,
                                  @RequestParam("id") int idCita)
    {
        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
            RecetaMedica receta = recetaMedicaRepository.buscarRecetaMedicaPorID(idReceta);
            model.addAttribute("receta", receta);
            Cita cita1 = citaRepository.buscarCitaPorId(idCita);
            model.addAttribute("cita", cita1);
        }
        return "doctor/editarReceta";
    }
    @PostMapping("/pacientesatendidos/verhistorial/vercita/actualizarreceta")
    @Transactional
    public String editarReceta(RedirectAttributes redirectAttributes,
                               @RequestParam("idReceta") int idReceta,
                               @RequestParam("id") int idCita,
                               @RequestParam("medicamento") String medicamento,
                               @RequestParam("dosis") String dosis,
                               @RequestParam("descripcion") String descripcion){
        recetaMedicaRepository.actualizarReceta( medicamento, dosis, descripcion, idCita, idReceta);
        redirectAttributes.addAttribute("idReceta",idReceta);
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";
    }

    @PostMapping("/pacientesatendidos/verhistorial/vercita/guardarreporte")
    @Transactional
    public String guardarReporte(RedirectAttributes redirectAttributes, @RequestParam("descripcion") String descripcion , @RequestParam("id") int idCita){
        Optional<ReporteCita> reporteCita = Optional.ofNullable(reporteCitaRepository.buscarReporteCitaPorId(idCita));
        if(reporteCita.isPresent()){
            reporteCitaRepository.actualizarReporteCita(descripcion, idCita);
        }else {
            reporteCitaRepository.añadirReporteCita(descripcion, idCita);
        }
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";

    }

    //consultar si se puede sacar idReceta
    @PostMapping("/pacientesatendidos/verhistorial/vercita/guardarreceta")
    @Transactional
    public String guardarReceta(RedirectAttributes redirectAttributes,
                                @RequestParam("medicamento") String medicamento ,
                                @RequestParam("dosis") String dosis,
                                @RequestParam("descripcion") String descripcion ,
                                //@RequestParam("idReceta") int idReceta,
                                @RequestParam("id") int idCita){

        recetaMedicaRepository.agregarReceta(medicamento,dosis,descripcion,idCita);
        redirectAttributes.addAttribute("id",idCita);
        //redirectAttributes.addAttribute("idReceta",idReceta);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";

    }

    @Transactional
    @GetMapping(value = "/pacientesatendidos/verhistorial/vercita/borrarreceta")
    public String borrarReceta(@RequestParam("idR") Integer idReceta, RedirectAttributes redirectAttributes){

        RecetaMedica receta = recetaMedicaRepository.buscarRecetaMedicaPorID(idReceta);
        Integer idCita = receta.getCita().getIdcita();
        recetaMedicaRepository.borrarReceta(idReceta);
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";
        }

    @GetMapping("/calendario")
    public String calendarioDoctor(Model model, @RequestParam("id") int idDoctor){
        //List<Event> events =
        List<Eventocalendariodoctor> events = eventocalendariodoctorRepository.calendarioPorDoctor(idDoctor);
        model.addAttribute("events", events);
        return "doctor/calendarioDoc";
    }

    @GetMapping("/cuestionario")
    public String cuestionarioDoctor(Model model, @RequestParam("id") int idPaciente){

        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
            //@RequestParam("id") int idDoc
            Paciente paciente1 = pacienteRepository.buscarPacientePorID(idPaciente);
            //Cita cita1= citaRepository.buscarCitaPorId(idCita);
            //Doctor doctor1 = doctorRepository.buscarDoctorPorId(idDoc);fcaalen
            List<Paciente> lista = pacienteRepository.findAll();
            model.addAttribute("paciente", paciente1);
            //model.addAttribute("cita", cita1);
            //model.addAttribute("doc", doctor1);
            //model.addAttribute("pacienteDatos", lista);
        }
        return "doctor/cuestionarioDoc";
    }

    @PostMapping("/envioCuestionario")
    public String enviarCuestionario(Model model, @RequestParam("pacienteId") int idP,
                                                @RequestParam("docId") int idD ){
        Cuestionario nuevocuestionario = new Cuestionario();
        cuestionarioRepository.save(nuevocuestionario);
        return "redirect:/doctor/cuestionario";
    }

    @GetMapping("/perfil")
    public String perfilDoctor(Model model,@RequestParam("id") int idDoctor) {

        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
        }
        return "doctor/perfilDoc";
    }

    @GetMapping("/mensajeria")
    public String mensajeriaDoctor(Model model, @RequestParam("id") int idD) {

        Optional<Doctor> optDoctor = doctorRepository.findById(2);

        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            //List<Mensaje> lista = sedeRepository.findAll();
            //model.addAttribute("listSedes", lista);
            model.addAttribute("doctor", doctor1);
        }
        return "doctor/mensajeriaDoc";
    }



    @GetMapping("/mensajeria/enviarmensaje")
    public String enviarMensajeDoctor() {
        return "doctor/enviarMensajeDoc";
    }

    @PostMapping("/mensajeria/enviarmensaje/envio")
    @Transactional
    //ResponseEntity<Void>
    public String sendEmail(@RequestParam("correodestino") String correoDestino, @RequestParam("asunto") String asunto, @RequestParam("descripcion") String descripcion) {
        emailService.sendEmail(correoDestino,asunto,descripcion);
        return "redirect:/doctor/mensajeria";
        //return ResponseEntity.ok().build();
    }

    @GetMapping("/notificaciones")
    public String notificacionesDoctor() {
        return "doctor/notificacionesDoc";
    }

    @PostMapping("/perfil/editarperfil")
    @Transactional
    public String actualizarPerfilDoctor(RedirectAttributes redirectAttributes,@RequestParam("id") int idDoctor,
                                         @RequestParam("idusuario") int idUsuario,@RequestParam("nombre") String nombres,
                                         @RequestParam("apellido") String apellidos,@RequestParam("dni") String dni,
                                         @RequestParam("correo") String correo){
        userRepository.actualizarPerfilDoctor(nombres,apellidos,dni,correo,idUsuario);
        redirectAttributes.addAttribute("id",idDoctor);
        return "redirect:/doctor/perfil";
    }

    @GetMapping("/configuraciones")
    public String configuracionDoctor(Model model, @RequestParam("id") int idD){
        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            List<Sede> lista = sedeRepository.findAll();
            model.addAttribute("listSedes", lista);
            model.addAttribute("doctor", doctor1);
        }
        return "doctor/configuracionDoc";
    }

    @PostMapping("/guardarSede")
    public String guardarSede(RedirectAttributes attr, @RequestParam("idsede") int idS,
                              @RequestParam("id") int idD){
        doctorRepository.cambiarSede(idS, idD);
        attr.addAttribute("id", idD);
        attr.addAttribute("iddoctor", idS);
        return "redirect:/doctor/configuraciones";
    }
    /*@PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return ResponseEntity.ok().build();

    }*/


}