package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.DoctorRepository;
import com.example.proyectogticsgrupo1.Repository.PacienteRepository;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private HttpSession session;


    @GetMapping(value = "/dashboard")
    public String dashboard(Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        return"administrativo/dashboard";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/crearpaciente";
    }

    @GetMapping(value = "/dashboarddoctores")
    public String dashDoc(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/dashboarddoctores";
    }

    @GetMapping(value = "/dashboardpacientes")
    public String dashPac(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/dashboardpaciente";
    }

    @GetMapping(value = "/formularioreferido")
    public String formRef(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/formularioreferido";
    }

    @GetMapping(value = "/invitar")
    public String invitar(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return "invitarpaciente";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/mensajes";
    }

    @GetMapping(value = "/notificaciones")
    public String notificaciones(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/notificaciones";
    }

    @GetMapping(value = "/nuevopaciente")
    public String nuevoPaciente(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return"administrativo/nuevopaciente";
    }
    @GetMapping(value = "/chat")
    public String chat(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return "administrativo/chat";
    }

    @GetMapping(value = "/detallesdoctor")
    public String detallesDoctor(){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return "administrativo/detallesdoctor";
    }

    @GetMapping(value = "/perfil")
    public String perfil(){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return "administrativo/perfil";
    }

    @GetMapping(value = "/configuracion")
    public String conf(){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return "administrativo/configuraciones";
    }

}
