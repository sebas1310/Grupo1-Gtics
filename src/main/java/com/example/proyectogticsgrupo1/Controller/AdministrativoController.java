package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.DoctorRepository;
import com.example.proyectogticsgrupo1.Repository.PacienteRepository;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/administrativo")
public class AdministrativoController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @GetMapping(value = "/dashboard")
    public String dashboard(){
        return"administrativo/dashboard";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente(){
        return"administrativo/crearpaciente";
    }

    @GetMapping(value = "/dashboarddoctores")
    public String dashDoc(){
        return"administrativo/dashboarddoctores";
    }

    @GetMapping(value = "/dashboardpacientes")
    public String dashPac(){
        return"administrativo/dashboardpaciente";
    }

    @GetMapping(value = "/formularioreferido")
    public String formRef(){
        return"administrativo/formularioreferido";
    }

    @GetMapping(value = "/invitar")
    public String invitar(){
        return "invitarpaciente";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes(){
        return"administrativo/mensajes";
    }

    @GetMapping(value = "/notificaciones")
    public String notificaciones(){
        return"administrativo/notificaciones";
    }

    @GetMapping(value = "/nuevopaciente")
    public String nuevoPaciente(){
        return"administrativo/nuevopaciente";
    }
    @GetMapping(value = "/chat")
    public String chat(){
        return "administrativo/chat";
    }

    @GetMapping(value = "/detallesdoctor")
    public String detallesDoctor(){
        return "administrativo/detallesdoctor";
    }

    @GetMapping(value = "/perfil")
    public String perfil(){
        return "administrativo/perfil";
    }

}
