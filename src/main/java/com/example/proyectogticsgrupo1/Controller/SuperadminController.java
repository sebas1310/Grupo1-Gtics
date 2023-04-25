package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Administrador;
import com.example.proyectogticsgrupo1.Entity.Especialidad;
import com.example.proyectogticsgrupo1.Entity.Superadmin;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/superadmin", method = RequestMethod.GET)
public class SuperadminController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    SedeRepository sedeRepository;
    @Autowired
    SeguroRepository seguroRepository;
    @Autowired
    TipoFormularioRepository tipoFormularioRepository;
    @Autowired
    UxUiRepository uxUiRepository;
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    SuperAdminRepository superAdminRepository;


    @GetMapping("/index")
    public String inicioDashboardSuperadmin(Model model){

        Optional<Administrador> optionalAdministrador = administradorRepository.findById(1);
        Administrador administrador = optionalAdministrador.get();
        model.addAttribute("administradores", administrador);

        return "superadmin/index";
    }
    @GetMapping("/listaform")
    public String listaFormularios(){
        return "superadmin/lista_formularios";
    }

    @GetMapping("/chat")
    public String chats(){
        return "superadmin/chat";
    }

    @GetMapping("/editarreportes")
    public String editarReportes(){
        return "superadmin/editar-reportes";
    }
    @GetMapping("/editarforms")
    public String editarForms(){
        return "superadmin/forms-editors";
    }
    @GetMapping("/registro")
    public String registrarUsuarios(){
        return "superadmin/pages-blank";
    }
    @GetMapping("/mensajeria")
    public String mensajeria(){
        return "superadmin/mensajeria";
    }

    @GetMapping("/perfil")
    public String perfilSuperAdmin(Model model){

        Optional<Superadmin> optionalSuperadmin = superAdminRepository.findById(1);
        Superadmin superadmin = optionalSuperadmin.get();
        model.addAttribute("superadminlog", superadmin);
        return "superadmin/users-profile";
    }
    @GetMapping("/registraradministrativo")
    public String registrarAdministrativo(){
        return "superadmin/pages-registrar-administrativo";
    }
    @GetMapping("/registraradministrador")
    public String registrarAdministrador(){
        return "superadmin/pages-registrar-adminitrador";
    }
    @GetMapping("/reportes")
    public String listaReportes(){
        return "superadmin/tables-general";
    }

    @GetMapping("/configuracion")
    public String configuraciones(){
        return "superadmin/configuraciones";
    }

    @GetMapping("/nuevoform")
    public String nuevoFormulario(){
        return "superadmin/nuevoformulario";
    }

    @GetMapping("/notificaciones")
    public String historialNotificaciones(){
        return "superadmin/historial-notificaciones";
    }
    @GetMapping("/perfilUsuario")
    public String perfilUsuario(){
        return "superadmin/perfil-usuarios";
    }

    @GetMapping("/seguros")
    public String seguro(){
        return "superadmin/seguros";
    }
}
