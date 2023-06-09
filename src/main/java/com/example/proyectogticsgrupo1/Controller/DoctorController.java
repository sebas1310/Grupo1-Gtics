package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    SedeRepository  sedeRepository;

    @Autowired
    CuestionarioRepository cuestionarioRepository;

    public DoctorController(CitaRepository citaRepository, DoctorRepository doctorRepository, PacienteRepository pacienteRepository,
                            RecetaMedicaRepository recetaMedicaRepository,ReporteCitaRepository reporteCitaRepository,UserRepository userRepository,
                            BitacoraDeDiagnosticoRepository bitacoraDeDiagnosticoRepository) {

        this.doctorRepository = doctorRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
        this.recetaMedicaRepository =recetaMedicaRepository;
        this.reporteCitaRepository = reporteCitaRepository;
        this.userRepository = userRepository;
        this.bitacoraDeDiagnosticoRepository= bitacoraDeDiagnosticoRepository;

    }


    //Mostrará Por Defecto el Calendario Semanal de Doctor

    @GetMapping("/dashboard")
    public String inicioDashboardDoctor(Model model) {

        Optional<Doctor> optDoctor = doctorRepository.findById(2);
        if (optDoctor.isPresent()) {
            Doctor doctor1 = optDoctor.get();
            model.addAttribute("doctor", doctor1);
            List<Cita> citasAgendadas1 = citaRepository.buscarCitasAgendadasDoctor(doctor1.getIdDoctor());
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
    public String pacientesAtendidosDoctor(Model model, @RequestParam("id") int idDoctor){

        model.addAttribute("pacientesAtendidosDoctor",citaRepository.pacientesAtendidosPorDoctor(idDoctor));
            return "doctor/pacientesAtendidos";
    }

    @GetMapping("/pacientesatendidos/verhistorial")
    public String historialPacienteDoctor(Model model, @RequestParam("id") int idPaciente){
        Paciente paciente1 = pacienteRepository.buscarPacientePorID(idPaciente);
        System.out.println("gaaaa");
        model.addAttribute("paciente",paciente1);
        model.addAttribute("citaspaciente",citaRepository.citasPorPaciente(idPaciente));
        model.addAttribute("bitacoradiagnostico",bitacoraDeDiagnosticoRepository.bitacoraDeDiagnostico(idPaciente));
        return "doctor/verHistorial";
    }

    @PostMapping("/pacientesatendidos/verhistorial/guardarbitacora")
    @Transactional
    public String guardarBitacora(RedirectAttributes redirectAttributes, @RequestParam("descripcion") String descripcion, @RequestParam("id") int idPaciente){
        bitacoraDeDiagnosticoRepository.guardarbitacora(descripcion,idPaciente);
        redirectAttributes.addAttribute("id",idPaciente);
        return "redirect:/doctor/pacientesatendidos/verhistorial";
    }

    @GetMapping("/pacientesatendidos/verhistorial/vercita")
    public String verCitaDoctor(Model model, @RequestParam("id") int idCita){
        Cita cita1 = citaRepository.buscarCitaPorId(idCita);
        model.addAttribute("cita",cita1);
        model.addAttribute("recetamedica",recetaMedicaRepository.buscarRecetaMedicaPorCita(idCita));
        model.addAttribute("reportecita",reporteCitaRepository.buscarReporteCitaPorId(idCita));
        return "doctor/verCita";
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
    @PostMapping("/doctor/pacientesatendidos/verhistorial/vercita/guardarreceta")
    @Transactional
    public String guardarReceta(RedirectAttributes redirectAttributes, @RequestParam("medicamento") String medicamento ,
                                @RequestParam("dosis") String dosis, @RequestParam("descripcion") String descripcion ,
                                @RequestParam("idcita") int idCita){

        recetaMedicaRepository.agregarReceta(medicamento,dosis,descripcion,idCita);
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";

    }
    @PostMapping("/doctor/pacientesatendidos/verhistorial/vercita/editarreceta")
    @Transactional
    public String editarReceta(RedirectAttributes redirectAttributes,@RequestParam("idR") int idReceta,
                                         @RequestParam("idcita") int idCita,
                                         @RequestParam("medicamento") String medicamentos,
                                         @RequestParam("dosis") String dosis,
                                         @RequestParam("descripcion") String descripcion){
        recetaMedicaRepository.actualizarReceta(medicamentos,dosis,descripcion,idCita);
        redirectAttributes.addAttribute("idcita",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";
    }

    @GetMapping("/calendario")
    public String calendarioDoctor(){

        return "doctor/calendarioDoc";
    }

    @GetMapping("/cuestionario")
    public String cuestionarioDoctor(Model model, @RequestParam("id") int idPaciente){
        //@RequestParam("id") int idDoc
        Paciente paciente1 = pacienteRepository.buscarPacientePorID(idPaciente);
        //Cita cita1= citaRepository.buscarCitaPorId(idCita);
        //Doctor doctor1 = doctorRepository.buscarDoctorPorId(idDoc);
        List<Paciente> lista = pacienteRepository.findAll();
        model.addAttribute("paciente", paciente1);
        //model.addAttribute("cita", cita1);
        //model.addAttribute("doc", doctor1);
        model.addAttribute("pacienteDatos", lista);

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
        Doctor doctor1 = doctorRepository.buscarDoctorPorId(idDoctor);
        model.addAttribute("doctor", doctor1);
        return "doctor/perfilDoc";
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
        Doctor doc = doctorRepository.buscarDoctorPorId(idD);
        List<Sede> lista = sedeRepository.findAll();
        model.addAttribute("listSedes", lista);
        model.addAttribute("doctor", doc);
        return "doctor/configuracionDoc";
    }

    @PostMapping("/guardarSede")
    public String guardarSede(RedirectAttributes attr, @RequestParam("sede_id") int idS,
                              @RequestParam("id") int idD){
        doctorRepository.cambiarSede(idS, idD);
        attr.addAttribute("id", idD);
        return "redirect:/doctor/configuraciones";
    }

}
