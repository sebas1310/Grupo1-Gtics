package com.example.proyectogticsgrupo1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/administrador", method = RequestMethod.GET)

public class AdministradorController {
    @GetMapping("")
    public String administrador() {
        return "administrador/dashboard";
        }

    @GetMapping(value = "/perfil")
    public String perfilAdministrador() {

        return "administrador/perfil";
    }
    @GetMapping(value = "/nuevopaciente")
    public String newPaciente() {
        return "administrador/nuevopaciente";
    }

    @GetMapping(value = "/calendariogeneral")
    public String genCalendar() {

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
    public String formatos() {

        return "administrador/formatos";
    }

    @GetMapping(value = "/configuraciones")
    public String config() {

        return "administrador/configuraciones";
    }

    @GetMapping(value = "/dashboardpaciente")
    public String dashboardpacient() {

        return "administrador/dashboardpaciente";
    }

    @GetMapping(value = "/dashboarddoctor")
    public String dashboarddoc() {

        return "administrador/dashboarddoctor";
    }

    @GetMapping(value = "/dashboardfinanzas")
    public String dashboardfinanz() {

        return "administrador/dashboardfinanzas";
    }

    @GetMapping(value = "/historialclinico")
    public String historialClinico() {

        return "administrador/historialclinico";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente() {

        return "administrador/crearpaciente";
    }

    @GetMapping(value = "/invitar")
    public String invitar() {

        return "administrador/invitar";
    }

    @GetMapping(value = "/vistaformato")
    public String vistaForm() {

        return "administrador/vistaformato";
    }

    @GetMapping(value = "/notificaciones")
    public String notif() {

        return "administrador/notificaciones";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes() {

        return "administrador/mensajes";
    }

    @GetMapping(value = "/chat")
    public String chat() {

        return "administrador/chat";
    }

    @GetMapping(value = "/detallesdoctor")
    public String Detalles() {

        return "administrador/detallesdoctor";
    }

    @GetMapping(value = "/vistacuestionario")
    public String VerCuestionario() {

        return "administrador/vistacuestionario";
    }

    @GetMapping(value = "/creardoctor")
    public String crearDoctor() {

        return "administrador/creardoctor";
    }






    }


