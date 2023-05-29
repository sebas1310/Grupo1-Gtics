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

    @Autowired
    TablaDatosLlenosRepository tablaDatosLlenosRepository;

    @Autowired
    TablaTitulosInputsRepository tablaTitulosInputsRepository;


    @GetMapping("/index")
    public String inicioDashboardSuperadmin(Model model){

//        Optional<Usuario> optionalUsuario = usuarioRepository.findById(1);
//        Usuario usuario = optionalUsuario.get();
//
//
//        List<Usuario> listaUsuarios = usuarioRepository.findAll();
//
//
//        model.addAttribute("administradores", usuario);
//
//        model.addAttribute("listaUsuarios", listaUsuarios);



        return "superadmin/index_spa";
    }
    @GetMapping("/listaform")
    public String listaFormularios(Model model){

        List<ModeloEntity> modeloEntityList = modeloRepository.findAll();
        model.addAttribute("modeloEntityList",modeloEntityList);


        return "superadmin/lista_plantillas_spa";
    }

    @GetMapping("/chat")
    public String chats(){
        return "/superadmin/chat_spa";
    }

    @GetMapping("/editarreportes")
    public String editarReportes(){
        return "superadmin/editar-reportes_spa";
    }
    @GetMapping("/editarforms")
    public String editarForms(){
        return "superadmin/forms-editors_spa";
    }
    @GetMapping("/registro")
    public String registrarUsuarios(){
        return "superadmin/pages-blank_spa";
    }
    @GetMapping("/mensajeria")
    public String mensajeria(){
        return "superadmin/mensajeria_spa";
    }

    @GetMapping("/perfil")
    public String perfilSuperAdmin(@ModelAttribute("superadminlog") Usuario usuario, Model model){
        Optional<Usuario> optionalSuperadmin = usuarioRepository.findById(1);
        usuario = optionalSuperadmin.get();
        model.addAttribute("superadminlog", usuario);
        return "superadmin/users-profile_spa";
    }
    @GetMapping("/registraradministrativo")
    public String registrarAdministrativo(@ModelAttribute("usuario") Usuario usuario, Model model, @RequestParam("t") String t){
        System.out.println(t);
        model.addAttribute("t",t);
        return "superadmin/pages-registrar-administrativo";
    }
    @GetMapping("/registraradministrador")
    public String registrarAdministrador(@ModelAttribute("usuario") Usuario usuario,Model model){
        return "superadmin/pages-registrar-adminitrador";
    }



    @PostMapping("/superadmin/actualizarUser")
    public String actualizarUser(Usuario usuario,RedirectAttributes attr){

        System.out.println(usuario.getNombres());
        System.out.println(usuario.getEstadohabilitado());

        if (usuario.getEstadohabilitado() == 0){
            int habilitado = 0;
            usuarioRepository.actualizarPaciente(habilitado,usuario.getNombres(),usuario.getApellidos(),usuario.getCorreo(),usuario.getDni(),usuario.getEdad(),usuario.getCelular(),usuario.getIdusuario());

        }else{
            int habilitado = 1;
            usuarioRepository.actualizarPaciente(habilitado,usuario.getNombres(),usuario.getApellidos(),usuario.getCorreo(),usuario.getDni(),usuario.getEdad(),usuario.getCelular(),usuario.getIdusuario());

        }

        if(usuario.getIdusuario()!=null){
            attr.addFlashAttribute("msg", "Usuario actualizado exitosamente");
        }
;
        return "redirect:/superadmin/index";
    }

    @PostMapping("/savespa")
    public String guardarSuperadmin(Usuario superadminlog, RedirectAttributes attr){
        System.out.println("id"+superadminlog.getIdusuario());

        attr.addFlashAttribute("msg","Superadmin actualizado");

        usuarioRepository.save(superadminlog);
        return "redirect:/superadmin/perfil";
    }

    @PostMapping("/save")
    public String guardarAdministrador(Usuario usuario, RedirectAttributes attr){
        System.out.println(usuario.getCelular());
        usuario.setContrasena(RandomStringUtils.random(10, true, true));

        if(usuario.getIdusuario()==null){
            attr.addFlashAttribute("msg", "Administrador creado exitosamente");
        }else{
            attr.addFlashAttribute("msg","Administrador actualizado");
        }
        usuarioRepository.save(usuario);
        return "redirect:/superadmin/registro";
    }




    @GetMapping("/delete")
    public String borrarUsuario(@RequestParam("id") int id, RedirectAttributes attr) {


        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isPresent()) {
            //usuaruiosRepository.eliminartipodeusuario(id);
            //usuaruiosRepository.actualizarsede(id);
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
        return "superadmin/tables-general_spa";
    }

    @GetMapping("/configuracion")
    public String configuraciones(){
        return "superadmin/configuraciones_spa";
    }

    @GetMapping("/nuevoform")
    public String nuevoFormulario(Model model){

        List<Tipodeusuario> tipodeusuarioList = tipodeusuarioRepository.findAll();
        List<Especialidad> especialidadList = especialidadRepository.findAll();

//        ModeloEntity modeloEntity = new ModeloEntity();
//        model.addAttribute("modelo",modeloEntity);
        model.addAttribute("tipodeusuarioList",tipodeusuarioList);
        model.addAttribute("especialidadList",especialidadList);


        return "superadmin/nuevoformulario_spa";
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

        /////////////////////////

        for (int i = 0; i < listaPreguntas.size(); i++) {
            String pregunta = listaPreguntas.get(i);
            System.out.println("pregunta:"+ pregunta);
            tablaTitulosInputsRepository.agregarNombreTitulos(pregunta);

        }


        /*tablaTitulosInputsRepository.agregarNuevaPlantilla(nombreplantilla,id_rol,id_especialidad,1);*/
        int id_registro_nuevo = 0;

        if(tipo_plantilla.equals("formulario")){
            tablaTitulosInputsRepository.agregarNuevoFormulario(nombreplantilla,id_rol,id_especialidad,1);
            id_registro_nuevo = tablaTitulosInputsRepository.contarRegistros();

        } else if (tipo_plantilla.equals("informe")) {
            tablaTitulosInputsRepository.agregarNuevoInforme(nombreplantilla,id_rol,id_especialidad,1);
            id_registro_nuevo = tablaTitulosInputsRepository.contarRegistros();

        } else if (tipo_plantilla.equals("cuestionario")) {
            tablaTitulosInputsRepository.agregarNuevoCuestionario(nombreplantilla,id_rol,id_especialidad,1);
            id_registro_nuevo = tablaTitulosInputsRepository.contarRegistros();

        }

        System.out.println("id: "+id_registro_nuevo);


        ///METODO LLENAR PLANTILLA INFORME //


        List<String> listaElementosDatosInputs = new ArrayList<>();

        listaElementosDatosInputs.add("respuesta1");
        listaElementosDatosInputs.add("respuesta2");
        listaElementosDatosInputs.add("respuesta3");
        listaElementosDatosInputs.add("respuesta4");
        listaElementosDatosInputs.add("respuesta5");

        for (String elemento : listaElementosDatosInputs) {
            tablaDatosLlenosRepository.agregarDatosDeInput(elemento);
        }

//        tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo,nombreplantilla,id_usuario,id_modelo,id_cita);
        tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo+2,nombreplantilla,4,7,1);



        //////////////////////////////////////
//        modeloRepository.crearnuevaPlantilla(nombreplantilla,mod_datos,id_rol,id_especialidad,nro_inputs);
//        if (employee.getEmployeeId() == 0) {
//            attr.addFlashAttribute("msg", "Plantilla creada exitosamente");
//        } else {
//            attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
//        }
//        attr.addFlashAttribute("msg", "Plantilla creada exitosamente");

        //BORRADO DE LA TABLA DE TITULOS.

        tablaTitulosInputsRepository.BorrarTitulosInput();
        tablaDatosLlenosRepository.BorrarDatosDeInput();


        return "redirect:/superadmin/nuevoform";
    }

    @GetMapping("/notificaciones")
    public String historialNotificaciones(){
        return "superadmin/historial-notificaciones_spa";
    }
    @GetMapping("/perfilUsuario")
    public String perfilUsuario(Model model,@RequestParam("id") int id){

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();

        System.out.println(id);
        model.addAttribute("usuario", usuario);

        return "superadmin/perfil-usuarios_spa";
    }

    @GetMapping("/seguros")
    public String seguro(Model model){
        List<Seguro> listSeguros = seguroRepository.findAll();
        model.addAttribute("listSeguros",listSeguros);
        return "superadmin/seguros_spa";
    }


    @PostMapping("/cambiarContrasena")
    public String cambiarContraseña(Model model, RedirectAttributes attr, @RequestParam("currentPassword") String currentPassword,
                                    @RequestParam("newPassword") String  newPassword,
                                    @RequestParam("renewpassword") String  renewpassword){

        String contrasenaActual = currentPassword;
        String nuevaContrasena = newPassword;
        String nuevaContrasena_v2 = renewpassword;




        if (nuevaContrasena == contrasenaActual && nuevaContrasena_v2 == renewpassword) {
            attr.addFlashAttribute("msg", "No se pudo actualizar la contraseña");
        } else {
            usuarioRepository.cambiarPassword(nuevaContrasena);
            attr.addFlashAttribute("msg", "Contraseña actualizada exitosamente");
        }



        return "redirect:/superadmin/perfil";
    }
}
