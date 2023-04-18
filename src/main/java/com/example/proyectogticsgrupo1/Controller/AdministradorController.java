package com.example.proyectogticsgrupo1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class AdministradorController {
    @GetMapping("/administrador")
    public String administrador() {
        return "administrador/dashboard";
        }

    @GetMapping(value = "/perfilAdministrador")
    public String perfilAdministrador() {

        return "administrador/perfil";
    }
    @GetMapping(value = "/nuevopaciente")
    public String newPaciente() {

        return "administrador/nuevopaciente";
    }

    @GetMapping(value = "/generalCalendario")
    public String genCalendar() {

        return "administrador/calendariogeneral";
    }

    @GetMapping(value = "/formatos")
    public String formatos() {

        return "administrador/formatos";
    }

    @GetMapping(value = "/configuraciones")
    public String config() {

        return "administrador/configuraciones";
    }






    }


