package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.GMailer;
import com.example.proyectogticsgrupo1.Repository.*;
import com.example.proyectogticsgrupo1.GMailer;
import com.example.proyectogticsgrupo1.Repository.ModeloJsonRepository;
import com.example.proyectogticsgrupo1.Service.EmailService;
/*import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;*/
import com.example.proyectogticsgrupo1.Service.imagenes.ImagenSubir;
import com.example.proyectogticsgrupo1.Service.imagenes.UploadInter;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;
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
    UploadInter uploadInter;

    @Autowired
    TipodeusuarioRepository tipodeusuarioRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Autowired
    ModeloRepository modeloRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    ModeloJsonRepository modeloJsonRepository;

    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    TablaDatosLlenosRepository tablaDatosLlenosRepository;

    @Autowired
    TablaTitulosInputsRepository tablaTitulosInputsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    UxUiRepository uxUiRepository;

    @GetMapping(value = "/email")
    public String emailpr() {
        String user = "alexia_jg@outlook.es";
        String subj = "jjj";
        String msj = "Pruebas de envio";
        emailService.sendEmail(user, subj, msj);
        return "redirect:/index";
    }

    @GetMapping(value = "**")
    public RedirectView redirectToDelivery() {
        return new RedirectView("/superadmin/index");
    }

    @GetMapping("/index")
    public String inicioDashboardSuperadmin(Model model){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");

        Tipodeusuario paciente = new Tipodeusuario();
        paciente.setIdtipodeusuario(4); paciente.setNombre("paciente");
        Tipodeusuario doctor = new Tipodeusuario();
        doctor.setIdtipodeusuario(5); doctor.setNombre("doctor");
        Tipodeusuario administrador = new Tipodeusuario();
        administrador.setIdtipodeusuario(2); administrador.setNombre("administrador");
        Tipodeusuario administrativo = new Tipodeusuario();
        administrativo.setIdtipodeusuario(3); administrativo.setNombre("administrativo");

        List<Usuario> listaPacientes = usuarioRepository.findAllByTipodeusuario(paciente);
        List<Usuario> listaDoctores = usuarioRepository.findAllByTipodeusuario(doctor);
        List<Usuario> listaAdministrativo = usuarioRepository.findAllByTipodeusuario(administrativo);
        List<Usuario> listaAdministrador = usuarioRepository.findAllByTipodeusuario(administrador);
        model.addAttribute("listapacientes", listaPacientes);
        model.addAttribute("listadoctores", listaDoctores);
        model.addAttribute("listaadministrativo", listaAdministrativo);
        model.addAttribute("listaadministrador", listaAdministrador);

        model.addAttribute("usuario", usuarioSpa);

        return "superadmin/index_spa";
    }

    @PostMapping("/guardarImagen")
    public  String subirImagenes(RedirectAttributes attr, @RequestParam("id") Integer id, @RequestParam("file") MultipartFile file)throws IOException{

        try{
            if (file!=null && !file.isEmpty()){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmmss");

                String filename = "perfilSuper" + formatter.format(date) + "." + file.getOriginalFilename().split("\\.")[1];
                ImagenSubir imagenSubir = new ImagenSubir();
                imagenSubir.setFilename(filename);
                imagenSubir.setFilebase64(Base64.getEncoder().encodeToString(file.getBytes()));

                String resultadoSubida = uploadInter.subirimagen(imagenSubir);


                if(resultadoSubida.equals("ok")){
                    System.out.println("https://lafe.blob.core.windows.net/clinicalafe/"+filename);
                    usuarioRepository.actualizarfotoperfilSpa("https://lafe.blob.core.windows.net/clinicalafe/"+filename, id);
                    session.removeAttribute("usuario");

                    session.setAttribute("usuario", (Usuario) usuarioRepository.findById(id).get());
                }

            }else {
                attr.addFlashAttribute("msg", "imagen subida exitosamente");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/superadmin/perfil";
    }


    @GetMapping("/dashboardpaciente")
    public String dashboardpacientes(Model model) {
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Tipodeusuario paciente = new Tipodeusuario();
        paciente.setIdtipodeusuario(4);
        paciente.setNombre("paciente");
        List<Usuario> listaPacientes = usuarioRepository.findAllByTipodeusuario(paciente);
        model.addAttribute("listapacientes", listaPacientes);
        model.addAttribute("usuario", usuarioSpa);
        return "superadmin/dashboardpaciente";
    }

    @GetMapping("/dashboarddoctor")
    public String dashboarddoctor(Model model){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Tipodeusuario doctor = new Tipodeusuario();
        doctor.setIdtipodeusuario(5);
        doctor.setNombre("doctor");
        List<Usuario> listaDoctores = usuarioRepository.findAllByTipodeusuario(doctor);
        model.addAttribute("listadoctores", listaDoctores);
        model.addAttribute("usuario", usuarioSpa);

        return "superadmin/dashboarddoctor";
    }
    @GetMapping("/dashboardadministrativo")
    public String dashboardadministrativo(Model model){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Tipodeusuario administrativo = new Tipodeusuario();
        administrativo.setIdtipodeusuario(3); administrativo.setNombre("administrativo");
        List<Usuario> listaAdministrativo = usuarioRepository.findAllByTipodeusuario(administrativo);
        model.addAttribute("listaadministrativo", listaAdministrativo);
        model.addAttribute("usuario", usuarioSpa);

        return "superadmin/dashboardadministrativo";
    }@GetMapping("/dashboaradministrador")
    public String dashboardadministrador(Model model){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Tipodeusuario administrador = new Tipodeusuario();
        administrador.setIdtipodeusuario(2); administrador.setNombre("administrador");
        List<Usuario> listaAdministrador = usuarioRepository.findAllByTipodeusuario(administrador);
        model.addAttribute("listaadministrador", listaAdministrador);
        model.addAttribute("usuario", usuarioSpa);

        return "superadmin/dashboaradministrador";
    }

    @GetMapping("/logueo")
    public String loguearse(@RequestParam ("id") Integer id){
        Usuario usuarioalt = usuarioRepository.findById(id).get();
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        session.removeAttribute("usuario");
        session.setAttribute("usuario", usuarioalt);
        session.setAttribute("superadmin", superadmin);
        UxUiEntity uxUiEntity = uxUiRepository.findByTipodeusuarioIdtipodeusuario(usuarioalt.getTipodeusuario().getIdtipodeusuario());
        session.setAttribute("configuiux", uxUiEntity.getCodigocolor());

        String ruta = "";
        switch (usuarioalt.getTipodeusuario().getIdtipodeusuario()){
            case 2:
                ruta = "administrador";
                break;
            case 3:
                ruta = "administrativo/dashboard";
                break;
            case 4:
                ruta = "paciente/";
                break;
            case 5:
                ruta = "doctor/dashboard";
                break;
            default:
                ruta = "superadmin/index";
        }

        return "redirect:/"+ ruta;
    }

    @GetMapping("/listaform")
    public String listaFormularios(Model model){

        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

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
    public String editarForms(Model model){
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        return "superadmin/forms-editors_spa";
    }
    @GetMapping("/registro")
    public String registrarUsuarios(Model model){
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);


        return "superadmin/pages-blank_spa";
    }
    @GetMapping("/mensajeria")
    public String mensajeria(Model model){
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        return "superadmin/mensajeria_spa";
    }

    @GetMapping("/perfil")
    public String perfilSuperAdmin(@ModelAttribute("superadminlog") Usuario usuario, Model model){
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Usuario superadmin = usuarioRepository.buscarPorId(usuarioSpa.getIdusuario());
        int edad = usuarioRepository.edad(superadmin.getIdusuario());
        superadmin.setEdad(edad);

        //Optional<Usuario> optionalSuperadmin = usuarioRepository.findById(1);
        //usuario = optionalSuperadmin.get();

        model.addAttribute("superadminlog", superadmin);
        return "superadmin/users-profile_spa";
    }

    @GetMapping("/registraradministrativo")
    public String registrarAdministrativo(@ModelAttribute("usuario") Usuario usuario, Model model){
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        //model.addAttribute("usuario", superadmin);
        model.addAttribute("listasedes", sedeRepository.findAll());
        model.addAttribute("listaEspecialidad", especialidadRepository.findAll());

        return "superadmin/pages-registrar-administrativo";
    }
    @GetMapping("/registraradministrador")
    public String registrarAdministrador(@ModelAttribute("usuario") Usuario usuario, Model model){
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        //model.addAttribute("usuario", superadmin);

        model.addAttribute("listasedes", sedeRepository.listaSedes());

        return "superadmin/pages-registrar-adminitrador";
    }



    @PostMapping("/actualizarUser")
    public String actualizarUser(Usuario usuario, RedirectAttributes attr, Model model, HttpSession session) {
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        Usuario superadmin = usuarioRepository.buscarPorId(usuarioSpa.getIdusuario());

        System.out.println(usuario.getNombres());
        System.out.println(usuario.getEstadohabilitado());

        model.addAttribute("listasedes", sedeRepository.findAll());

        if (usuario.getEstadohabilitado() == 0) {
            int habilitado = 0;
            usuarioRepository.actualizarPaciente(habilitado, usuario.getNombres(), usuario.getApellidos(), usuario.getCorreo(), usuario.getDni(), usuario.getFechanacimiento(), usuario.getCelular(), usuario.getIdusuario());

        } else {
            int habilitado = 1;
            usuarioRepository.actualizarPaciente(habilitado, usuario.getNombres(), usuario.getApellidos(), usuario.getCorreo(), usuario.getDni(), usuario.getFechanacimiento(), usuario.getCelular(), usuario.getIdusuario());

        }

        if (usuario.getIdusuario() != null) {
            attr.addFlashAttribute("msg", "Usuario actualizado exitosamente");
        }

        // Guardar el ID en la variable de sesión
        session.setAttribute("usuarioId", usuario.getIdusuario());

        return "redirect:/superadmin/perfilUsuario";
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
    public String guardarAdministrador(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes attr, Model model, @RequestParam("especialidad") int idEspecialidad) throws Exception {

        System.out.println("sede" + usuario.getSede());
        usuario.setContrasena(RandomStringUtils.random(10, true, true));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        model.addAttribute("listaEspecialidad", especialidadRepository.findAll());


        if(bindingResult.hasErrors()){
            model.addAttribute("listasedes", sedeRepository.listaSedes());
            model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
            return "superadmin/pages-registrar-adminitrador";
        }else{
            Usuario existingUserDni = usuarioRepository.findByDni(usuario.getDni());
            Usuario existingUserCelular = usuarioRepository.findByCelular(usuario.getCelular());
            Usuario existingUserCorreo = usuarioRepository.findByCorreo(usuario.getCorreo());

            if(existingUserDni == null){
                model.addAttribute("listasedes", sedeRepository.listaSedes());
                model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
                if(existingUserCelular == null){
                    model.addAttribute("listasedes", sedeRepository.listaSedes());
                    model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
                    if(existingUserCorreo == null){
                        model.addAttribute("listasedes", sedeRepository.listaSedes());
                        model.addAttribute("listaEspecialidad", especialidadRepository.findAll());
                        attr.addFlashAttribute("msg", "Administrador creado");
                        String contrasenaGenerada = generarContrasena(10);
                        usuario.setContrasena(passwordEncoder.encode(contrasenaGenerada));
                        Especialidad especialidad = new Especialidad();
                        especialidad.setIdespecialidad(idEspecialidad);
                        usuario.setEspecialidad(especialidad);
                        usuarioRepository.save(usuario);

                        int edad = usuarioRepository.edad(usuario.getIdusuario());
                        usuario.setEdad(edad);
                        usuarioRepository.save(usuario);


                        Email from = new Email("clinica.lafe.info@gmail.com");
                        String subject = "Confirmación de Registro";
                        Email to = new Email(usuario.getCorreo());
                        Content content_2 = new Content("text/plain", "Estimado usuario,"+"\n usted ha sido registrado en la Clínica La Fe "+"\nTu contraseña por defecto es: " + contrasenaGenerada + "\nIngresa"+ " aquí" +"para cambiarla : http://34.29.54.187:8083/cambiarcontrasena");
                        Mail mail = new Mail(from, subject, to, content_2);

                        SendGrid sg = new SendGrid("llavesuper");  //aca va el cambio por wsp poner esto
                        Request request = new Request();
                        try {
                            request.setMethod(Method.POST);
                            request.setEndpoint("mail/send");
                            request.setBody(mail.build());
                            Response response = sg.api(request);
                            System.out.println(response.getStatusCode());
                            System.out.println(response.getBody());
                            System.out.println(response.getHeaders());
                        } catch (IOException ex) {
                            throw ex;
                        }

                        //emailService.sendEmail(usuario.getCorreo(), "Confirmación de Registro", "Estimado usuario,"+"\n usted ha sido registrado en la Clínica La Fe "+"\nTu contraseña por defecto es: " + contrasenaGenerada + "\nIngresa"+ " aquí" +"para cambiarla : http://34.29.54.187:8083/cambiarcontrasena");
                        /* Construye el mensaje del correo en formato HTML
                        enviocorreo.sendMail("Registro Exitoso en Clinica La Fe", "Estimado usuario,"
                                + "\nUsted ha sido registrado en Clínica LA FE."
                                + "\nSu contraseña por defecto es: " + contrasenaGenerada
                                + "\nPor favor, ingrese a: http://34.29.54.187:8083/cambiarcontrasena aquí para cambiarla.", receiverEmail);

                        */
                        return "redirect:/superadmin/index";


                    }else{
                        bindingResult.rejectValue("correo", "error.correo", "Ya existe un usuario con este correo electrónico");
                        model.addAttribute("listasedes", sedeRepository.listaSedes());
                        model.addAttribute("listaespecialidad", especialidadRepository.findAll());
                        return "superadmin/pages-registrar-adminitrador";
                    }
                }else{
                    bindingResult.rejectValue("celular", "error.celular", "Ya existe un usuario con este número de celular");

                    return "superadmin/pages-registrar-adminitrador";
                }
            }else{
                bindingResult.rejectValue("dni", "error.dni", "Ya existe un usuario con este DNI");

                return "superadmin/pages-registrar-adminitrador";
            }
        }
    }



    @GetMapping("/delete")
    public String borrarUsuario(@RequestParam("id") int id, RedirectAttributes attr, Model model) {
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

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
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

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
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        Optional<Seguro> optSeguro = seguroRepository.findById(id);
        if(optSeguro.isPresent()){
            Seguro seguro = optSeguro.get();
            model.addAttribute("seguro", seguro);
            return "superadmin/editSeguro";
        }else{
            return "redirect:/seguros";
        }
    }

    @PostMapping("/editarSeguro")
    public String editarSeguros1(@RequestParam("id") int id, Model model){
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

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
    public String listaReportes(Model model){

        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        return "superadmin/tables-general_spa";
    }
    @GetMapping("/configuracion")
    public String configuraciones(Model model){

        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        return "superadmin/configuracion_elige";
    }

    @GetMapping("/configuracionUX")
    public String configuracionesUX(Model model){

        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        return "superadmin/configuraciones_spa";
    }

    @GetMapping("/configuracionUI")
    public String configuracionesUI(Model model){

        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        return "superadmin/configuracionesUI";
    }

    @PostMapping("/editarUI")
    public String editarConfUI(@RequestParam("tema") String tema,
                               @RequestParam("color") String color,
                               @RequestParam("imagen") String imagen,
                               @RequestParam("idRol") Integer idrol){

        UxUiEntity uxUiEntity = uxUiRepository.findByTipodeusuarioIdtipodeusuario(idrol);
        if (uxUiEntity!=null){
            if (!uxUiEntity.getCodigocolor().equals(color))
               uxUiEntity.setCodigocolor(color);
            uxUiRepository.save(uxUiEntity);
            session.removeAttribute("configuiux");
            session.setAttribute("configuiux", uxUiRepository.findByTipodeusuarioIdtipodeusuario(1).getCodigocolor());

        }else{
            UxUiEntity uxUiEntity1 = new UxUiEntity();
            uxUiEntity1.setTipodeusuarioIdtipodeusuario(idrol);
            uxUiEntity1.setCodigocolor(color);
            uxUiRepository.save(uxUiEntity1);
        }
        return "redirect:/superadmin/configuracion";

    }

    @GetMapping("/nuevoform")
    public String nuevoFormulario(Model model){


        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

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
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

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

        //tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo+4,nombreplantilla,4,12,1);
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
    public String historialNotificaciones(Model model){

        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);


        return "superadmin/historial-notificaciones_spa";
    }

    @PostMapping("/perfilUsuario")
    public String perfilUsuario(Model model, @RequestParam("id") int id) {
        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        model.addAttribute("listasedes", sedeRepository.findAll());

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();
        int edad = usuarioRepository.edad(usuario.getIdusuario());
        usuario.setEdad(edad);
        model.addAttribute("persona", usuario);
        System.out.println(id);

        return "superadmin/perfil-usuarios_spa";
    }

    @GetMapping("/perfilUsuario")
    public String perfilUsuario(Model model, HttpSession session) {
        // Obtener el ID almacenado en la variable de sesión
        Integer idUsuario = (Integer) session.getAttribute("usuarioId");

        Usuario usuarioSpa = (Usuario) session.getAttribute("usuario");
        model.addAttribute("listasedes", sedeRepository.findAll());

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optionalUsuario.get();
        int edad = usuarioRepository.edad(usuario.getIdusuario());
        usuario.setEdad(edad);
        model.addAttribute("persona", usuario);
        System.out.println(idUsuario);

        return "superadmin/perfil-usuarios_spa";
    }



    @GetMapping("/seguros")
    public String seguro(Model model){
        Usuario superadmin = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", superadmin);

        List<Seguro> listSeguros = seguroRepository.findAll();
        model.addAttribute("listSeguros",listSeguros);
        return "superadmin/seguros_spa";
    }


    @PostMapping("/changepasswordusuarios")
    @Transactional
    public String changePasswordUsuarios(Usuario usuario, RedirectAttributes attr, Model model, HttpSession session,
                                         @RequestParam("id") int idusuario,
                                         @RequestParam("contrasena1") String contrasena,
                                         @RequestParam("newpassword2") String newpassword,
                                         @RequestParam("renewpassword3") String renewpassword,
                                         RedirectAttributes redirectAttributes) throws IOException {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idusuario);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();

            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                if (newpassword.equals(renewpassword)) {
                    String hashedNewPassword = passwordEncoder.encode(newpassword);
                    usuarioRepository.changePassword(hashedNewPassword, usuario.getIdusuario());


                    //emailService.sendEmail(usuario.getCorreo(), "Cambio de Contraseña", "Estimado usuario, hemos restablecido su contraseña, la cual ahora es: " + newpassword);
                    Email from = new Email("clinica.lafe.info@gmail.com");
                    String subject = "Cambio de Contraseña";
                    Email to = new Email(usuario.getCorreo());
                    Content content_2 = new Content("text/plain", "Estimado usuario, hemos restablecido su contraseña, la cual ahora es: " + newpassword);
                    Mail mail = new Mail(from, subject, to, content_2);

                    SendGrid sg = new SendGrid("llavesuper");  //aca va el cambio por wsp poner esto
                    Request request = new Request();
                    try {
                        request.setMethod(Method.POST);
                        request.setEndpoint("mail/send");
                        request.setBody(mail.build());
                        Response response = sg.api(request);
                        System.out.println(response.getStatusCode());
                        System.out.println(response.getBody());
                        System.out.println(response.getHeaders());
                    } catch (IOException ex) {
                        throw ex;
                    }


                    redirectAttributes.addFlashAttribute("psw3", "Contraseña actualizada");
                } else {
                    redirectAttributes.addFlashAttribute("psw4", "Las contraseñas no coinciden");
                }
            } else {
                redirectAttributes.addFlashAttribute("psw4", "La contraseña actual es incorrecta");
            }
        }

        session.setAttribute("usuarioId", usuario.getIdusuario());
        return "redirect:/superadmin/perfilUsuario";
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