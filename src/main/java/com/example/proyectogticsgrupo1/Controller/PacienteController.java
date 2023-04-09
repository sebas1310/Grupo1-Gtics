package com.example.proyectogticsgrupo1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/paciente")
public class PacienteController {
    @GetMapping(value = "/")
    public String paciente(){
        return "paciente/index";
    }

    @GetMapping(value = "/perfil")
    public String perfilPaciente(){
        return "paciente/perfil";
    }

    @GetMapping(value = "/agendarCita")
    public String agendarCita(){
        return "paciente/agendarCita";
    }


}
