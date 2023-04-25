package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.ReporteCita;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.transaction.Transactional;
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

    @GetMapping("/calendario")
    public String calendarioDoctor(){

        return "doctor/calendarioDoc";
    }

    @GetMapping("/cuestionario")
    public String cuestionarioDoctor(){

        return "doctor/cuestionarioDoc";
    }

    @GetMapping("/perfil")
    public String perfilDoctor(Model model,@RequestParam("id") int idDoctor){
        Doctor doctor1 = doctorRepository.buscarDoctorPorId(idDoctor);
        model.addAttribute("doctor", doctor1);
        return "doctor/perfilDoc";
    }

    @GetMapping("/configuraciones")
    public String configuracionDoctor(){

        return "doctor/configuracionDoc";
    }

}
