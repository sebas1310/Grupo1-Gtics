package com.example.proyectogticsgrupo1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/doctor",method = RequestMethod.GET)
public class DoctorController {


    //Mostrará Por Defecto el Calendario Semanal de Doctor
    @GetMapping("/dashboard")
    public String inicioDashboardDoctor(){

        return "doctor/dashboardDoc";
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
    public String pacientesAtendidosDoctor(){

        return "doctor/pacientesAtendidos";
    }

    @GetMapping("/pacientesatendidos/verhistorial")
    public String historialPacienteDoctor(){

        return "doctor/verHistorial";
    }

    @GetMapping("/pacientesatendidos/verhistorial/vercita")
    public String verCitaDoctor(){

        return "doctor/verCita";
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
    public String perfilDoctor(){

        return "doctor/perfilDoc";
    }

    @GetMapping("/configuraciones")
    public String configuracionDoctor(){

        return "doctor/configuracionDoc";
    }

}
