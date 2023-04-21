package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/paciente")
public class PacienteController {

    final SedeRepository sedeRepository;
    final EspecialidadRepository especialidadRepository;
    final DoctorRepository doctorRepository;
    final UserRepository userRepository;
    final PacienteRepository pacienteRepository;
    final TipoCitaRepository tipoCitaRepository;

    public PacienteController(SedeRepository sedeRepository, EspecialidadRepository especialidadRepository, DoctorRepository doctorRepository, UserRepository userRepository, PacienteRepository pacienteRepository, TipoCitaRepository tipoCitaRepository) {
        this.sedeRepository = sedeRepository;
        this.especialidadRepository = especialidadRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.pacienteRepository = pacienteRepository;
        this.tipoCitaRepository = tipoCitaRepository;
    }

    @GetMapping(value = "/")
    public String paciente(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        model.addAttribute("docs", doctorRepository.findAll());
        model.addAttribute("sedes", sedeRepository.findAll());
        return "paciente/index";
    }

    @GetMapping(value = "/perfilDoctor")
    public String perfilDoc(@RequestParam("iddoc") Integer iddoc, Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(iddoc);
        if(optionalDoctor.isPresent()){
            Doctor doctor=optionalDoctor.get();
            model.addAttribute("doc",doctor);
            return "paciente/perfilDoctor";
        }
        else {
            return "redirect:/paciente/";
        }

    }
    @GetMapping(value = "/selecTipoCita")
    public String selecTipoCita(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/tipocita";
    }
    @GetMapping(value = "/reservar2")
    public String selectDate(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/reservar2";
    }

    @GetMapping(value = "/pagos")
    public String pagosView(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/pagos";
    }

    @GetMapping(value = "/perfil")
    public String perfilPaciente(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/perfil";
    }

    @GetMapping(value = "/agendarCita")
    public String agendarCita(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        List<Sede> listsede = sedeRepository.findAll();
        List<Especialidad> listespecialidad = especialidadRepository.findAll();
        model.addAttribute("sedesparacitas", listsede);
        model.addAttribute("especialidades", listespecialidad);
        model.addAttribute("pacientelog",paciente);
        model.addAttribute("doctores",doctorRepository.findAll());
        model.addAttribute("tipocita",tipoCitaRepository.findAll());
        return "paciente/agendarCita";
    }
    @GetMapping(value = "/historialCitas")
    public String historialCitas(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/historialCitas";
    }

    @GetMapping(value = "/calendarioMensual")
    public String calendarioMensual(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/calendarioMensual";
    }


    @PostMapping(value = "/alergia")
    @Transactional
    public String modAlergia(@RequestParam("alergias") String alergia, @RequestParam("idpaciente") Integer id){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        String alg = paciente.getAlergias()+","+alergia;
        pacienteRepository.modificarAlergia(alg, id);
        return "redirect:/paciente/perfil";
    }


    @PostMapping(value = "/changepassword")
    @Transactional
    public String changePassword(@RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        if(paciente.getUsuario().getContrasena().equals(contrasena)){
            if(newpassword.equals(renewpassword)){
                userRepository.changePassword(renewpassword,paciente.getUsuario().getIdusuario());
            }
            else {
                //no coincide se envia mensaje
                return "redirect:/paciente/perfil";
            }
        }else {
            return "redirect:/paciente/perfil";
        }

        return "redirect:/paciente/perfil";
    }

}
