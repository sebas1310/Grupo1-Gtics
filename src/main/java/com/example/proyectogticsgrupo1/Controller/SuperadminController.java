package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;

import com.example.proyectogticsgrupo1.Repository.ModeloJsonRepository;
import com.example.proyectogticsgrupo1.Service.EmailService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Encoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/superadmin")
public class SuperadminController {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";

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
    ModeloJsonRepository modeloJsonRepository;


    @Autowired
    TablaDatosLlenosRepository tablaDatosLlenosRepository;

    @Autowired
    TablaTitulosInputsRepository tablaTitulosInputsRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private HttpSession session;

    @GetMapping(value = "/email")
    public String emailpr() {
        String user = "alexia_jg@outlook.es";
        String subj = "jjj";
        String msj = "Pruebas de envio";
        emailService.sendEmail(user, subj, msj);
        return "redirect:/index";
    }
    @GetMapping("/index")
    public String inicioDashboardSuperadmin(Model model){

        //Optional<Usuario> optionalUsuario = usuarioRepository.findById(1);
        //Usuario usuario = optionalUsuario.get();
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Usuario superadmin = usuarioRepository.buscarPorId(usuarioSpa.getIdusuario());

        model.addAttribute("usuario",superadmin);

        List<Usuario> listaUsuarios = usuarioRepository.findAll();


        model.addAttribute("administradores", superadmin);

        model.addAttribute("listaUsuarios", listaUsuarios);



        return "superadmin/index_spa";
    }
    @GetMapping("/listaform")
    public String listaFormularios(Model model){

        List<ModeloJsonEntity> modeloEntityList = modeloJsonRepository.listarPlantillas();
        model.addAttribute("modeloEntityList",modeloEntityList);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String rol = userDetails.getAuthorities().toString();
        String password = userDetails.getPassword();

        System.out.println(username);
        System.out.println(rol.getClass());
        System.out.println(rol);
        System.out.println(password);

        model.addAttribute("rol_user_autorizado",rol);


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
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Usuario superadmin = usuarioRepository.buscarPorId(usuarioSpa.getIdusuario());

        //Optional<Usuario> optionalSuperadmin = usuarioRepository.findById(1);
        //usuario = optionalSuperadmin.get();

        model.addAttribute("superadminlog", superadmin);
        return "superadmin/users-profile_spa";
    }

    @GetMapping("/registraradministrativo")
    public String registrarAdministrativo(@ModelAttribute("usuario") Usuario usuario, Model model){

        model.addAttribute("listasedes", sedeRepository.findAll());
        return "superadmin/pages-registrar-administrativo";
    }
    @GetMapping("/registraradministrador")
    public String registrarAdministrador(@ModelAttribute("usuario") Usuario usuario, Model model){

        model.addAttribute("listasedes", sedeRepository.listaSedes());

        return "superadmin/pages-registrar-adminitrador";
    }



    @PostMapping("/superadmin/actualizarUser")
    public String actualizarUser(Usuario usuario,RedirectAttributes attr, Model model){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Usuario superadmin = usuarioRepository.buscarPorId(usuarioSpa.getIdusuario());

        System.out.println(usuario.getNombres());
        System.out.println(usuario.getEstadohabilitado());

        model.addAttribute("listasedes", sedeRepository.findAll());

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

    @PostMapping("/saveSeguro")
    public String guardarSeguro(@ModelAttribute("seguro") @Valid Seguro seguro, BindingResult bindingResult,
                                Model model, RedirectAttributes attr){
        System.out.println("id"+seguro.getNombre());
        if(bindingResult.hasErrors()){
            return "superadmin/editSeguro";
        }else{
            seguroRepository.save(seguro);
            return "redirect:/superadmin/seguros";

        }
    }

    public static String generarContrasena(int longitud) {
        StringBuilder sb = new StringBuilder(longitud);
        Random random = new Random();

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
    @PostMapping("/save")
    public String guardarAdministrador(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes attr, Model model){

        System.out.println("sede" + usuario.getSede());
        usuario.setContrasena(RandomStringUtils.random(10, true, true));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(bindingResult.hasErrors()){

            model.addAttribute("listasedes", sedeRepository.listaSedes());
            return "superadmin/pages-registrar-adminitrador";

        }else{

            Usuario existingUserDni = usuarioRepository.findByDni(usuario.getDni());
            Usuario existingUserCelular = usuarioRepository.findByCelular(usuario.getCelular());
            Usuario existingUserCorreo = usuarioRepository.findByCorreo(usuario.getCorreo());

            if(existingUserDni == null){
                if(existingUserCelular == null){
                    if(existingUserCorreo==null){
                        attr.addFlashAttribute("msg","Administrador creado");
                        String contrasenaGenerada = generarContrasena(10);
                        usuario.setContrasena(passwordEncoder.encode(contrasenaGenerada));
                        usuarioRepository.save(usuario);
                        emailService.sendEmail(usuario.getCorreo(), "Confirmación de Registro", "Estimado usuario, usted ha sido registrado en Clinica LA FE y su contraseña por defecto es: " + contrasenaGenerada );
                        return "redirect:/superadmin/index";
                    }else{
                        bindingResult.rejectValue("correo", "error.correo", "Ya existe un usuario con este correo electrónico");
                        model.addAttribute("listasedes", sedeRepository.listaSedes());
                        return "superadmin/pages-registrar-adminitrador";
                    }
                }else{
                    bindingResult.rejectValue("celular", "error.celular", "Ya existe un usuario con este número de celular");
                    model.addAttribute("listasedes", sedeRepository.listaSedes());
                    return "superadmin/pages-registrar-adminitrador";
                }
            }else{
                bindingResult.rejectValue("dni", "error.dni", "Ya existe un usuario con este DNI");
                model.addAttribute("listasedes", sedeRepository.listaSedes());
                return "superadmin/pages-registrar-adminitrador";
            }
        }
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
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if(optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            model.addAttribute("usuario", usuario);
            return "superadmin/users-profile_spa";
        }else{
            return "redirect:/superadmin/index";
        }
    }

    @GetMapping("/editarSeguro")
    public String editarSeguros(@RequestParam("id") int id, Model model){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");

        Optional<Seguro> optSeguro = seguroRepository.findById(id);
        if(optSeguro.isPresent()){
            Seguro seguro = optSeguro.get();
            model.addAttribute("seguro", seguro);
            return "superadmin/editSeguro";
        }else{
            return "redirect:/seguros";
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

    @ResponseBody
    @GetMapping(value = "/listarTitulos")
    public List<String> listarTitulos(Model model, @RequestParam("id_de_modelo_plantilla") int id_de_modelo_plantilla){

        System.out.println("llega al repo de listar");

//        int id_model = Integer.parseInt(id_de_modelo_plantilla);

        return modeloJsonRepository.listarPreguntasxPlantilla(id_de_modelo_plantilla);

    }


    @ResponseBody
    @PostMapping(value = "/BorrarPlantilla")
    public int BorrarPlantilla(Model model,@RequestParam("id_de_modelo_plantilla") int id_de_modelo_plantilla
    ){
        System.out.println("llega al repo de borrado");
        System.out.println(id_de_modelo_plantilla);

//        modeloJsonRepository.borrarPlantillas(id_de_modelo_plantilla);



        return modeloJsonRepository.borrarPlantillas(id_de_modelo_plantilla);

    }



    @ResponseBody
    @PostMapping(value = "/modificarPlantilla")
    public String modificarPlantilla(Model model, @RequestParam("valores") List<String> valores){
        System.out.println("llega al repo de modificar");
        System.out.println(valores);


        String primerValor_id = valores.get(0);

        System.out.println(primerValor_id);

        valores.remove(0);

        int primerValorInt_id = Integer.parseInt(String.valueOf(primerValor_id));



        ModeloJsonEntity EncontrarModelo = modeloJsonRepository.buscarModeloEdit(primerValorInt_id);


        String nbr_plantilla = EncontrarModelo.getNombrePlantilla();
        int id_especialidad = EncontrarModelo.getEspecialidad().getIdespecialidad();
        int id_tipo_usuario = EncontrarModelo.getTipodeusuario().getIdtipodeusuario();
        Byte flg_formulario = EncontrarModelo.getFormulario();
        Byte flg_cuestionario = EncontrarModelo.getCuestionario();

//        if(EncontrarModelo.getCuestionario() ==null){


        Byte flg_informe = EncontrarModelo.getInforme();

        System.out.println("nbr_plantilla="+nbr_plantilla);
        System.out.println("id_especialidad="+id_especialidad);
        System.out.println("id_tipo_usuario="+id_tipo_usuario);
        System.out.println("flg_formulario="+flg_formulario);
        System.out.println("flg_cuestionario="+flg_cuestionario);
        System.out.println("flg_informe="+flg_informe);


        modeloJsonRepository.borrarPlantillas(primerValorInt_id);


        for (int i = 0; i < valores.size(); i++) {
            String pregunta = valores.get(i);
            System.out.println("pregunta:"+ pregunta);
            tablaTitulosInputsRepository.agregarNombreTitulos(pregunta);

        }



        if(flg_formulario != null){
            tablaTitulosInputsRepository.agregarNuevoFormulario(nbr_plantilla,id_tipo_usuario,id_especialidad,1);

        } else if (flg_informe != null) {
            tablaTitulosInputsRepository.agregarNuevoInforme(nbr_plantilla,id_tipo_usuario,id_especialidad,1);

        } else if (flg_cuestionario != null) {
            tablaTitulosInputsRepository.agregarNuevoCuestionario(nbr_plantilla,id_tipo_usuario,id_especialidad,1);

        }





        tablaTitulosInputsRepository.BorrarTitulosInput();



        //sacar valores del registro con el id(flags)
        //deletear
        //insertar en tabla flotante las preguntas y volver a crear el registro con el id eliminado









//        modeloJsonRepository.borrarPlantillas(id_de_modelo_plantilla);



        return "hola";

    }







    @PostMapping(value = "/crearPlantillaInforme")
    @Transactional
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

//
//
//
//        tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo,nombreplantilla,4,1,1);

        tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo+4,nombreplantilla,4,12,1);
        //para llenar en datos_json



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
        //jalar para borrar
        tablaDatosLlenosRepository.BorrarDatosDeInput();


        return "redirect:/superadmin/nuevoform";
    }


    @GetMapping("/notificaciones")
    public String historialNotificaciones(){
        return "superadmin/historial-notificaciones_spa";
    }

    @GetMapping("/perfilUsuario")
    public String perfilUsuario(Model model,@RequestParam("id") int id){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        // Usuario superadmin = usuarioRepository.buscarPorId(usuarioSpa.getIdusuario());

        model.addAttribute("listasedes", sedeRepository.findAll());

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();
        model.addAttribute("persona",usuario);
        System.out.println(id);
        // model.addAttribute("usuario", superadmin);

        return "superadmin/perfil-usuarios_spa";
    }

    @GetMapping("/seguros")
    public String seguro(Model model){
        List<Seguro> listSeguros = seguroRepository.findAll();
        model.addAttribute("listSeguros",listSeguros);
        return "superadmin/seguros_spa";
    }

    @PostMapping(value = "/changepasswordusuarios")
    @Transactional
    public String changePasswordUsuarios(@RequestParam("id") int idusuario,
                                 @RequestParam("contrasena1") String contrasena,
                                 @RequestParam("newpassword2") String newpassword,
                                 @RequestParam("renewpassword3") String renewpassword, RedirectAttributes redirectAttributes) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idusuario);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(optionalUsuario.isPresent()){

            Usuario usuario = optionalUsuario.get();

            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                String hashedNewPassword = passwordEncoder.encode(newpassword);

                usuarioRepository.changePassword(hashedNewPassword, usuario.getIdusuario());
                emailService.sendEmail(usuario.getCorreo(), "Cambio de Contraseña", "Estimado usuario, hemos reestablecido su contraseña, la cual ahora es: " + newpassword);
                redirectAttributes.addFlashAttribute("psw3", "Contraseña actualizada");
            } else {
                System.out.println("INCORRECTO");
                redirectAttributes.addFlashAttribute("psw4", "La contraseña es incorrecta");
            }

        }else{

        }

        return "redirect:/superadmin/perfilUsuario?id=" + idusuario;
    }

    @PostMapping(value = "/cambiarcontrasena")
    @Transactional
    public String changePassword(@RequestParam("id") int idusuario,
                                 @RequestParam("contrasena") String contrasena,
                                 @RequestParam("newpassword") String newpassword,
                                 @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes) {

        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(contrasena, superadmin.getContrasena())) {
            String hashedNewPassword = passwordEncoder.encode(newpassword);

            usuarioRepository.changePassword(hashedNewPassword, superadmin.getIdusuario());
            redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada");

        } else {
            System.out.println("INCORRECTO");
            redirectAttributes.addFlashAttribute("psw2", "La contraseña es incorrecta");
        }

        return "redirect:/superadmin/perfil";
    }
}