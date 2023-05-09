package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/superadmin")
public class SuperadminController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    SedeRepository sedeRepository;
    @Autowired
    SeguroRepository seguroRepository;

    @Autowired
    TipodeusuarioRepository tipodeusuarioRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Autowired
    ModeloRepository modeloRepository;




    @GetMapping("/index")
    public String inicioDashboardSuperadmin(Model model){

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(1);
        Usuario usuario = optionalUsuario.get();


        List<Usuario> listaUsuarios = usuarioRepository.findAll();


        model.addAttribute("administradores", usuario);

        model.addAttribute("listaUsuarios", listaUsuarios);



        return "/superadmin/index_spa";
    }
    @GetMapping("/listaform")
    public String listaFormularios(Model model){

        List<ModeloEntity> modeloEntityList = modeloRepository.findAll();
        model.addAttribute("modeloEntityList",modeloEntityList);


        return "/superadmin/lista_plantillas_spa";
    }

    @GetMapping("/chat")
    public String chats(){
        return "/superadmin/chat_spa";
    }

    @GetMapping("/editarreportes")
    public String editarReportes(){
        return "/superadmin/editar-reportes_spa";
    }
    @GetMapping("/editarforms")
    public String editarForms(){
        return "/superadmin/forms-editors_spa";
    }
    @GetMapping("/registro")
    public String registrarUsuarios(){
        return "/superadmin/pages-blank_spa";
    }
    @GetMapping("/mensajeria")
    public String mensajeria(){
        return "/superadmin/mensajeria_spa";
    }

    @GetMapping("/perfil")
    public String perfilSuperAdmin(Model model){


        Optional<Usuario> optionalSuperadmin = usuarioRepository.findById(1);
        Usuario usuario = optionalSuperadmin.get();
        model.addAttribute("superadminlog", usuario);
        return "/superadmin/users-profile_spa";
    }
    @GetMapping("/registraradministrativo")
    public String registrarAdministrativo(Model model){

        return "/superadmin/pages-registrar-administrativo";
    }
    @GetMapping("/registraradministrador")
    public String registrarAdministrador(Model model){

        return "superadmin/pages-registrar-adminitrador";
    }



    @PostMapping("/superadmin/actualizarUser")
    public String actualizarUser(Usuario usuario,RedirectAttributes attr){

//        if (usuario.getEstadohabilitado() == null){
//
//        }
//        Usuario usuario = usuario.getEstadohabilitado();
//        System.out.println(usuario.getEstadohabilitado());
//        usuarioRepository.actualizarPaciente()
//
//
//        if(usuario.getIdusuario()!=null){
//            attr.addFlashAttribute("msg", "Usuario actualizado exitosamente");
//        }
;
        return "redirect:/superadmin/index";
    }

    @PostMapping("/save")
    public String guardarAdministrador(Usuario usuario, RedirectAttributes attr){
        usuario.setContrasena(RandomStringUtils.random(10, true, true));
        usuario.setEstadohabilitado(1);
        Tipodeusuario admin = new Tipodeusuario();
        admin.setIdtipodeusuario(3);
        usuario.setTipodeusuario(admin);

        System.out.println("llega a guardar"+ usuario);
        if(usuario.getIdusuario()==null){
            attr.addFlashAttribute("msg", "Administrador creado exitosamente");
        }else{
            attr.addFlashAttribute("msg","Administrador actualizado");
        }
        System.out.println("genero"+ usuario.getGenero());
        usuario.setGenero("Femenino");
        usuarioRepository.save(usuario);
        return "redirect:/superadmin/registro";
    }




    @GetMapping("/delete")
    public String borrarUsuario(@RequestParam("id") int id, RedirectAttributes attr) {


        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isPresent()) {
            //usuaruiosRepository.eliminarmanager(id);
            //usuaruiosRepository.actualizardepartamento(id);
            //usuaruiosRepository.eliminarempleado(id);
            attr.addFlashAttribute("msg" ,"Usuario borrado");
        }

        return "redirect:/superadmin/index";

    }


    @GetMapping("/edit")
    public String editarUsuario(Model model, @RequestParam("id") int id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if(optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            model.addAttribute("usuario", usuario);
            return "users-profile_spa";
        }else{
            return "redirect:/superadmin/index";
        }
    }
    @GetMapping("/reportes")
    public String listaReportes(){
        return "/superadmin/tables-general_spa";
    }

    @GetMapping("/configuracion")
    public String configuraciones(){
        return "/superadmin/configuraciones_spa";
    }

    @GetMapping("/nuevoform")
    public String nuevoFormulario(Model model){

        List<Tipodeusuario> tipodeusuarioList = tipodeusuarioRepository.findAll();
        List<Especialidad> especialidadList = especialidadRepository.findAll();

//        ModeloEntity modeloEntity = new ModeloEntity();
//        model.addAttribute("modelo",modeloEntity);
        model.addAttribute("tipodeusuarioList",tipodeusuarioList);
        model.addAttribute("especialidadList",especialidadList);


        return "/superadmin/nuevoformulario_spa";
    }


    @PostMapping("/guardarCambiosPlantilla")
    public String guardarCambiosPlantilla(Model model, @RequestParam("nuevoNombrePlantilla") String nuevoNombrePlantilla
            , @RequestParam("id_fila") int id_fila){
        System.out.println(nuevoNombrePlantilla);

        modeloRepository.actualizarPlantilla(nuevoNombrePlantilla,id_fila);

        return "redirect:/superadmin/listaform";

    }

    @PostMapping("/crearPlantillaInforme")
    public String crearPlantillaInforme(Model model,@RequestParam("datos") String datos
            ,@RequestParam("nombreplantilla") String nombreplantilla
            ,@RequestParam("id_rol") int id_rol
            ,@RequestParam("id_especialidad") int id_especialidad
            ,@RequestParam("nro_inputs") int nro_inputs
            ,@RequestParam("tipo_plantilla") String tipo_plantilla
            ){
//        , RedirectAttributes attr

//        ,@RequestParam("nombreplantilla") String nombreplantilla

        System.out.println("nombre_plantilla:"+nombreplantilla);
        System.out.println("id_rol:"+id_rol);
        System.out.println("id_especialidad:"+id_especialidad);
        System.out.println("nro_inputs:"+nro_inputs);
        System.out.println(tipo_plantilla);
//        System.out.println("preguntas:"+datos);

        String mod_datos = datos.substring(1,datos.length());

        System.out.println(mod_datos);

        List<String> listaPreguntas = new ArrayList<>();

        listaPreguntas = List.of(mod_datos.split(Pattern.quote("|")));

        System.out.println("preguntas:"+listaPreguntas);
        
        if(tipo_plantilla.equals("formulario")){
            modeloRepository.crearnuevaPlantillaForms(nombreplantilla,mod_datos,id_rol,id_especialidad,nro_inputs,1);
            
        } else if (tipo_plantilla.equals("informe")) {
            modeloRepository.crearnuevaPlantillaInforme(nombreplantilla,mod_datos,id_rol,id_especialidad,nro_inputs,1);
            
        } else if (tipo_plantilla.equals("cuestionario")) {
            modeloRepository.crearnuevaPlantillaCuestionario(nombreplantilla,mod_datos,id_rol,id_especialidad,nro_inputs,1);

        }

//        modeloRepository.crearnuevaPlantilla(nombreplantilla,mod_datos,id_rol,id_especialidad,nro_inputs);



//        if (employee.getEmployeeId() == 0) {
//            attr.addFlashAttribute("msg", "Plantilla creada exitosamente");
//        } else {
//            attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
//        }

//        attr.addFlashAttribute("msg", "Plantilla creada exitosamente");


        return "redirect:/superadmin/nuevoform";
    }

    @GetMapping("/notificaciones")
    public String historialNotificaciones(){
        return "/superadmin/historial-notificaciones_spa";
    }
    @GetMapping("/perfilUsuario")
    public String perfilUsuario(Model model,@RequestParam("id") int id){

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();

        System.out.println(id);
        model.addAttribute("usuario", usuario);

        return "/superadmin/perfil-usuarios_spa";
    }

    @GetMapping("/seguros")
    public String seguro(Model model){
        List<Seguro> listSeguros = seguroRepository.findAll();
        model.addAttribute("listSeguros",listSeguros);
        return "/superadmin/seguros_spa";
    }


    @PostMapping("/cambiarContraseña")
    public String cambiarContraseña(Model model, RedirectAttributes attr, @RequestParam("currentPassword") String currentPassword,
                                    @RequestParam("newPassword") String  newPassword,
                                    @RequestParam("renewpassword") String  renewpassword){

        String contraseñaActual = currentPassword;
        String nuevaContraseña = newPassword;
        String nuevaContraseña_v2 = renewpassword;




        if (nuevaContraseña == contraseñaActual && nuevaContraseña_v2 == renewpassword) {
            attr.addFlashAttribute("msg", "No se pudo actualizar la contraseña");
        } else {
            usuarioRepository.cambiarPassword(nuevaContraseña);
            attr.addFlashAttribute("msg", "Contraseña actualizada exitosamente");
        }



        return "redirect:/superadmin/perfil";
    }
}
