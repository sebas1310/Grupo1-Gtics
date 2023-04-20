package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Repository.EspecialidadRepository;
import com.example.proyectogticsgrupo1.Repository.SedeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/paciente")
public class PacienteController {

    final SedeRepository sedeRepository;
    final EspecialidadRepository especialidadRepository;

    public PacienteController(SedeRepository sedeRepository, EspecialidadRepository especialidadRepository) {
        this.sedeRepository = sedeRepository;
        this.especialidadRepository = especialidadRepository;
    }

    @GetMapping(value = "/")
    public String paciente(Model model){
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("sedes", sedeRepository.findAll());
        return "paciente/index";
    }

    @GetMapping(value = "/perfilDoctor")
    public String perfilDoc(){
        return "paciente/perfilDoctor";
    }
    @GetMapping(value = "/selecTipoCita")
    public String selecTipoCita(){
        return "paciente/tipocita";
    }
    @GetMapping(value = "/reservar2")
    public String selectDate(){
        return "paciente/reservar2";
    }

    @GetMapping(value = "/pagos")
    public String pagosView(){
        return "paciente/pagos";
    }

    @GetMapping(value = "/perfil")
    public String perfilPaciente(){
        return "paciente/perfil";
    }

    @GetMapping(value = "/agendarCita")
    public String agendarCita(){
        return "paciente/agendarCita";
    }
    @GetMapping(value = "/historialCitas")
    public String historialCitas(){
        return "paciente/historialCitas";
    }

    @GetMapping(value = "/calendarioMensual")
    public String calendarioMensual(){
        return "paciente/calendarioMensual";
    }



}
