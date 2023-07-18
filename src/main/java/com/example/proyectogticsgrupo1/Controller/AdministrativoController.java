package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.Doctor;
import com.example.proyectogticsgrupo1.Entity.MailCorreo;
import com.example.proyectogticsgrupo1.Entity.Paciente;
import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Repository.*;
import com.example.proyectogticsgrupo1.Service.EmailService;
import com.example.proyectogticsgrupo1.Service.imagenes.ImagenSubir;
import com.example.proyectogticsgrupo1.Service.imagenes.UploadInter;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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
    private EmailService emailService;

    @Autowired
    UploadInter uploadInter;

    @Autowired
    MailCorreoRepository mailCorreoRepository;

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/dashboard")
    public String administrador(Model model) {
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        List<Paciente> listaPacientesSD = pacienteRepository.listarPacienteporSedeyEspecialidadDashboard(usuarioAdministrativo.getSede().getIdsede(), usuarioAdministrativo.getEspecialidad().getIdespecialidad()); // a futuro cambiar
        model.addAttribute("listaUsuariosPacientes", listaPacientesSD);
        List<Doctor> listaDoctoresSD = doctorRepository.listarDoctorporSedeyEspecialidadDashboard(usuarioAdministrativo.getSede().getIdsede(),usuarioAdministrativo.getEspecialidad().getIdespecialidad());
        model.addAttribute("listaUsuarioDoctores", listaDoctoresSD);
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/dashboard";
    }

    @GetMapping(value = "/crearpaciente")
    public String crearPaciente(Model model, @RequestParam(value = "mensaje",required = false) String mensaje){
        System.out.println(mensaje);
        if(mensaje != null){
            model.addAttribute("mensaje", "Validación correcta");
        }
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/crearpaciente";

    }

    @GetMapping(value = "**")
    public RedirectView redirectToDelivery() {
        return new RedirectView("/administrativo/dashboard");
    }





    @GetMapping(value = "/dashboarddoctores")
    public String dashDoc(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        List<Doctor> listaDoctorSD = doctorRepository.listarDoctorporSedeyEspecialidadDashboardDoctores(usuarioAdministrativo.getSede().getIdsede(), usuarioAdministrativo.getEspecialidad().getIdespecialidad()); // a futuro cambiar
        model.addAttribute("listaUsuariosDoctores", listaDoctorSD);
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/dashboarddoctores";
    }

    @GetMapping(value = "/dashboardpacientes")
    public String dashPac(Model model){

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        List<Paciente> listaPacientesSD = pacienteRepository.listarPacienteporSedeyEspecialidadDashboardPacientes(usuarioAdministrativo.getSede().getIdsede(), usuarioAdministrativo.getEspecialidad().getIdespecialidad()); // a futuro cambiar
        model.addAttribute("listaUsuariosPacientes", listaPacientesSD);
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/dashboardpaciente";
    }


    @Transactional
    @ResponseBody
    @PostMapping(value = "/consultaReniec")

    public ResponseEntity<String> consultaReniec(Model model, @RequestParam("nombre") String nombre, @RequestParam("dni") String dni,
                                                 RedirectAttributes redirectAttributes) {
        String token = "apis-token-4620.hXLROh-1FS43jVJ6Zxg06j2CMigvq9eK";
        String dni_2 = dni;
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + dni_2;

        System.out.println(nombre);

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Referer", "https://apis.net.pe/consulta-dni-api");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                System.out.println(response.toString());

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                // Comprueba si el objeto contiene el nombre
                if (jsonObject.has("nombres")) {
                    String nombreObtenido = jsonObject.get("nombres").getAsString();
                    String apellido1 = jsonObject.get("apellidoPaterno").getAsString();
                    String apellido2 = jsonObject.get("apellidoMaterno").getAsString();
                    String nombrecompleto = nombreObtenido + " " + apellido1 + " " + apellido2;
                    System.out.println(nombrecompleto);

                    if (nombrecompleto.equals(nombre)) {
                        System.out.println("validacion_correcta");
                        redirectAttributes.addFlashAttribute("mensaje", "Validación correcta");
                        model.addAttribute("mensaje", "Validación correcta");
                        return ResponseEntity.ok("Validación correcta");
                    } else {
                        System.out.println("Validación incorrecta");
                        return ResponseEntity.badRequest().body("Validación incorrecta");

                    }
                } else {
                    System.out.println("El objeto JSON no contiene la clave 'nombres'");
                }

                // Aquí puedes trabajar con los demás datos del objeto JSON obtenidos
            } else {
                System.out.println("Error: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Validación incorrecta");

    }





    @GetMapping(value = "/formularioreferido")
    public String formRef(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/formularioreferido";
    }

    @GetMapping(value = "/formularioreferido2")
    public String formRef2(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/formularioreferido2";
    }

    @GetMapping(value = "/invitar")
    public String invitar(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "invitarpaciente";
    }

    @GetMapping(value = "/mensajes")
    public String mensajes(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        model.addAttribute("listamensajes", mailCorreoRepository.buscarMensajesEnviadosyRecibidos(usuarioAdministrativo.getIdusuario(),usuarioAdministrativo.getIdusuario()));

        return"administrativo/mensajes";
    }

    @GetMapping(value = "/notificaciones")
    public String notif(Model model) {
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        model.addAttribute("notificaciones",notificacionesRepository.notificacionesPorUsuario(usuarioAdministrativo.getIdusuario()));
        return "administrativo/notificaciones";
    }

    @GetMapping(value = "/nuevopaciente")
    public String nuevoPaciente(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return"administrativo/nuevopaciente";
    }
    @GetMapping(value = "/chat")
    public String chat(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/chat";
    }

    @PostMapping(value = "/enviarmensaje")
    public String enviarMensaje(@RequestParam("correo") String correo,
                                @RequestParam("asunto") String asunto,
                                @RequestParam("descripcion") String descripcion,
                                RedirectAttributes redirectAttributes, Model model) {
        // Verificar si el correo existe en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);

        if (usuario != null) {
            // Crear un nuevo mensaje y asignar los valores
            MailCorreo mensaje = new MailCorreo();
            mensaje.setAsunto(asunto);
            mensaje.setDescripcion(descripcion);
            mensaje.setCorreodestino(correo);
            mensaje.setCorreo(usuario.getCorreo());
            Usuario usuarioo = new Usuario();
            usuarioo.setIdusuario(usuarioAdministrativo.getIdusuario());
            mensaje.setUsuarioOrigen(usuarioo);
            Usuario usuariod = new Usuario();
            usuariod.setIdusuario(usuario.getIdusuario());
            mensaje.setUsuarioDestino(usuariod);
            // Establecer la fecha y hora actual
            mensaje.setFecha(LocalDate.now());
            mensaje.setHora(LocalTime.now());
            mensaje.setPassword("1234");

            // Guardar el mensaje en la base de datos
            mailCorreoRepository.save(mensaje);

            // Lógica para enviar el correo electrónico
            String mensajeCorreo = "Asunto: " + asunto + "\nDescripción: " + descripcion;
            emailService.sendEmail(correo, "Mensaje de Contacto", mensajeCorreo);



            redirectAttributes.addFlashAttribute("mp1", "El correo ha sido enviado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("mp2", "No se puede comunicar con el correo ingresado");
        }

        return "redirect:/administrativo/chat";
    }

    @PostMapping(value = "/changepassword")
    @Transactional
    public String changePassword(@RequestParam("id") int idusuario,@RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes) {

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if (passwordEncoder.matches(contrasena, usuarioAdministrativo.getContrasena())) {
            String hashedNewPassword = passwordEncoder.encode(newpassword);


            //usuarioRepository.changePassword(renewpassword, usuarioAdministrador.getIdusuario());
            usuarioRepository.changePassword(hashedNewPassword, usuarioAdministrativo.getIdusuario());
            redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada");

        } else {
            redirectAttributes.addFlashAttribute("psw2", "La contraseña es incorrecta");
        }

        return "redirect:/administrativo/perfil";
    }

    @GetMapping(value = "/detallesdoctor")
    public String detallesDoctor(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/detallesdoctor";
    }

    @GetMapping(value = "/perfil")
    public String perfil(@ModelAttribute("administrativolog") Usuario usuario,Model model){

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        Usuario administrativo = usuarioRepository.buscarPorId(usuarioAdministrativo.getIdusuario());
        model.addAttribute("usuario", usuarioAdministrativo);
        int edad = usuarioRepository.edad(administrativo.getIdusuario());
        administrativo.setEdad(edad);
        model.addAttribute("administrativolog", administrativo);
        return "administrativo/perfil";
    }

    @PostMapping(value = "/editarperfil")
    public String editarPerfil(RedirectAttributes redirectAttributes,
                               @RequestParam("id") int idusuario,
                               @RequestParam("nombres") String nombres,
                               @RequestParam("apellidos") String apellidos,
                               @RequestParam("correo") String correo,
                               @RequestParam("celular") String celular) {

        usuarioRepository.perfil(nombres, apellidos, correo, celular, idusuario);
        session.removeAttribute("usuario");
        session.setAttribute("usuario", usuarioRepository.findById(idusuario).get());
        redirectAttributes.addFlashAttribute("msg", "Perfil Actualizado");
        return "redirect:/administrativo/perfil";
    }

    @GetMapping(value = "/configuracion")
    public String conf(Model model){
        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);
        return "administrativo/configuraciones";
    }


    @PostMapping(value = "/enviarcorreoadministrativo")
    @Transactional
    public String enviarCorreo(@RequestParam("nombres") String nombres, @RequestParam("dni") String dni,
                               @RequestParam("correo") String correo, RedirectAttributes redirectAttributes, Model model) {
        // Verificar si existe un usuario con el mismo DNI
        Usuario usuarioDni = usuarioRepository.findByDni(dni);

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);

        // Verificar si existe un usuario con el mismo correo electrónico
        Usuario usuarioCorreo = usuarioRepository.findByCorreo(correo);

        if (usuarioDni != null && usuarioCorreo != null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, ya existe un usuario con este DNI y correo electrónico");
        } else if (usuarioDni != null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, ya existe un usuario con este DNI");
        } else if (usuarioCorreo != null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, ya existe un usuario con este correo electrónico");
        } else {
            // Lógica para enviar el correo electrónico
            emailService.sendEmail(correo, "Invitación",
                    "Estimado usuario, usted ha sido invitado a la plataforma de Clínica LA FE:\nIngresa aquí para registrarte: http://localhost:8083/formularioReferido");
            String content = "Usted invito un usuario con CORREO: " + correo ;
            String titulo = "Invitación enviada existosamente";
            notificacionesRepository.notificarCreacion2(usuarioAdministrativo.getIdusuario(),content,titulo);
            redirectAttributes.addFlashAttribute("ms1", "El correo ha sido enviado exitosamente");
        }

        return "redirect:/administrativo/crearpaciente";
    }

    @PostMapping(value = "/enviarcorreoadministrativo1")
    @Transactional
    public String enviarCorreo1(@RequestParam("correo") String correo, RedirectAttributes redirectAttributes, Model model) {

        Usuario usuarioAdministrativo = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioAdministrativo);

        // Verificar si existe un usuario con el mismo correo electrónico
        Usuario usuarioCorreo = usuarioRepository.findByCorreo(correo);

        if (usuarioCorreo != null) {
            redirectAttributes.addFlashAttribute("ms2", "No se pudo enviar el correo, ya existe un usuario con este correo electrónico");
        } else {
            // Lógica para enviar el correo electrónico
            emailService.sendEmail(correo, "Invitación",
                    "Estimado usuario, usted ha sido invitado a la plataforma de Clínica LA FE:\nIngresa aquí para registrarte: http://34.29.54.187:8083//formularioReferido");
            String content = "Usted invito un usuario con CORREO: " + correo ;
            String titulo = "Invitación enviada existosamente";
            notificacionesRepository.notificarCreacion2(usuarioAdministrativo.getIdusuario(),content,titulo);
            redirectAttributes.addFlashAttribute("ms1", "El correo ha sido enviado exitosamente");
        }

        return "redirect:/administrativo/crearpaciente";
    }

    @PostMapping("/guardarImagen")
    public  String subirImagenes(RedirectAttributes attr, @RequestParam("id") Integer id, @RequestParam("file") MultipartFile file)throws IOException {

        try{
            if (file!=null && !file.isEmpty()){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmmss");

                String filename = "perfilAdministrativo" + id + "." + formatter.format(date) + "." + file.getOriginalFilename().split("\\.")[1];
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
        return "redirect:/administrativo/perfil";
    }






//    @ResponseBody
//    @PostMapping(value = "/consultaReniec")
//    public int BorrarPlantilla(Model model,@RequestParam("id_de_modelo_plantilla") int id_de_modelo_plantilla
//    ){
//        System.out.println("llega al repo de borrado");
//        System.out.println(id_de_modelo_plantilla);
//
////        modeloJsonRepository.borrarPlantillas(id_de_modelo_plantilla);
//
//
//
//        return modeloJsonRepository.borrarPlantillas(id_de_modelo_plantilla);
//
//    }
}
