package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/doctor")
public class DoctorController {
    final DoctorRepository doctorRepository;
    final PacienteRepository pacienteRepository;
    final CitaRepository citaRepository;
    final RecetaMedicaRepository recetaMedicaRepository;
    final ReporteCitaRepository reporteCitaRepository;
    final UsuarioRepository usuarioRepository;
    final BitacoraDeDiagnosticoRepository bitacoraDeDiagnosticoRepository;
    final SedeRepository  sedeRepository;

    final CuestionarioRepository cuestionarioRepository;

    final EventocalendariodoctorRepository eventocalendariodoctorRepository;

    final BoletaDoctorRepository boletaDoctorRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    MailCorreoRepository mailCorreoRepository;


    public DoctorController(CitaRepository citaRepository, DoctorRepository doctorRepository, PacienteRepository pacienteRepository,
                            RecetaMedicaRepository recetaMedicaRepository, ReporteCitaRepository reporteCitaRepository,UsuarioRepository usuarioRepository,
                            BitacoraDeDiagnosticoRepository bitacoraDeDiagnosticoRepository,
                            EventocalendariodoctorRepository eventocalendariodoctorRepository, CuestionarioRepository cuestionarioRepository, SedeRepository sedeRepository, BoletaDoctorRepository boletaDoctorRepository) {

        this.doctorRepository = doctorRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
        this.recetaMedicaRepository =recetaMedicaRepository;
        this.reporteCitaRepository = reporteCitaRepository;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraDeDiagnosticoRepository= bitacoraDeDiagnosticoRepository;
        this.eventocalendariodoctorRepository = eventocalendariodoctorRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.sedeRepository = sedeRepository;
        this.boletaDoctorRepository = boletaDoctorRepository;
    }

    @Autowired
    private HttpSession session;

    //Mostrará Por Defecto el Calendario Semanal de Doctor

    @GetMapping("/dashboard")
    public String inicioDashboardDoctor(Model model) {

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            List<Cita> citasAgendadas1 = citaRepository.buscarCitasAgendadasDoctor(doctor.getIddoctor());

            model.addAttribute("citasAgendadas",citasAgendadas1);
            return "doctor/dashboardDoc";
        }

    @GetMapping("/dashboard/diario")
    public String inicioDashboardDoctor2(Model model){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);


        return "doctor/dashboardDocDiario";
    }

    @GetMapping("/dashboard/mensual")
    public String inicioDashboardDoctor3(Model model){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);

        return "doctor/dashboardDocMensual";
    }


    @GetMapping("/pacientesatendidos")
    public String pacientesAtendidosDoctor(Model model) {

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            model.addAttribute("pacientesAtendidosDoctor", citaRepository.pacientesAtendidosPorDoctor(doctor.getIddoctor()));

        return "doctor/pacientesAtendidos";
    }

    @GetMapping("/pacientesatendidos/verhistorial")
    public String historialPacienteDoctor(Model model, @RequestParam("id") int idPaciente){

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            Paciente paciente1 = pacienteRepository.buscarPacientePorID(idPaciente);
            model.addAttribute("paciente", paciente1);
            model.addAttribute("citaspaciente", citaRepository.citasPorPaciente(idPaciente,doctor.getIddoctor()));
            model.addAttribute("bitacoradiagnostico", bitacoraDeDiagnosticoRepository.bitacoraDeDiagnostico(idPaciente));

            return "doctor/verHistorial";
    }

    @PostMapping("/pacientesatendidos/verhistorial/guardarbitacora")
    @Transactional
    public String guardarBitacora(RedirectAttributes redirectAttributes, @RequestParam("descripcion") String descripcion, @RequestParam("id") int idPaciente){
        bitacoraDeDiagnosticoRepository.guardarbitacora(descripcion,idPaciente);
        redirectAttributes.addFlashAttribute("msg","Bitácora Guardada");
        redirectAttributes.addAttribute("id",idPaciente);
        return "redirect:/doctor/pacientesatendidos/verhistorial";
    }

    @GetMapping("/pacientesatendidos/verhistorial/borrarbitacora")
    @Transactional
    public String borrarBitacora(RedirectAttributes redirectAttributes, @RequestParam("idB") int idBitacora){
        BitacoraDeDiagnostico bitacora = bitacoraDeDiagnosticoRepository.buscarBitacoraDeDiagnosticoID(idBitacora);
        Integer idPaciente = bitacora.getPaciente().getIdpaciente();
        bitacoraDeDiagnosticoRepository.borrarbitacora(idBitacora);
        redirectAttributes.addFlashAttribute("msg2","Bitácora Borrada");
        redirectAttributes.addAttribute("id",idPaciente);
        return "redirect:/doctor/pacientesatendidos/verhistorial";
    }

    @GetMapping("/pacientesatendidos/verhistorial/boleta")
    public String verBoletaDoctor(Model model, @RequestParam("id") int idCita ){

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            BoletaDoctor boletaDoctor = boletaDoctorRepository.buscarBoletaDoctorCita(idCita);
            model.addAttribute("boletadoctor", boletaDoctor);

        return "doctor/boletaDoc";
    }

    @GetMapping("/pacientesatendidos/verhistorial/vercita")
    public String verCitaDoctor(Model model, @RequestParam("id") int idCita,
                                @RequestParam(name="idReceta", defaultValue = "0") int idReceta) {

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            Optional<Cita> optCita = citaRepository.findById(idCita);
            Cita cita =optCita.get();
            model.addAttribute("cita", cita);
            model.addAttribute("recetamedica", recetaMedicaRepository.buscarRecetaMedicaPorCita(idCita, idReceta));
            model.addAttribute("reportecita", reporteCitaRepository.buscarReporteCitaPorId(idCita));

        return "doctor/verCita";
    }
    @GetMapping("/pacientesatendidos/verhistorial/vercita/editarreceta")
    public String verEditarReceta(Model model, @RequestParam("idReceta") int idReceta,
                                  @RequestParam("id") int idCita) {

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            RecetaMedica receta = recetaMedicaRepository.buscarRecetaMedicaPorID(idReceta);
            model.addAttribute("receta", receta);
            Cita cita1 = citaRepository.buscarCitaPorId(idCita);
            model.addAttribute("cita", cita1);

        return "doctor/editarReceta";
    }
    @PostMapping("/pacientesatendidos/verhistorial/vercita/actualizarreceta")
    @Transactional
    public String editarReceta(RedirectAttributes redirectAttributes,
                               @RequestParam("idReceta") int idReceta,
                               @RequestParam("id") int idCita,
                               @RequestParam("medicamento") String medicamento,
                               @RequestParam("dosis") String dosis,
                               @RequestParam("descripcion") String descripcion){
        recetaMedicaRepository.actualizarReceta( medicamento, dosis, descripcion, idCita, idReceta);
        redirectAttributes.addAttribute("idReceta",idReceta);
        redirectAttributes.addFlashAttribute("msg3","Receta Actualizada");
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";
    }

    @PostMapping("/pacientesatendidos/verhistorial/vercita/guardarreporte")
    @Transactional
    public String guardarReporte(RedirectAttributes redirectAttributes, @RequestParam("descripcion") String descripcion , @RequestParam("id") int idCita){
        Optional<ReporteCita> reporteCita = Optional.ofNullable(reporteCitaRepository.buscarReporteCitaPorId(idCita));
        if(reporteCita.isPresent()){
            reporteCitaRepository.actualizarReporteCita(descripcion, idCita);
        }else {
            reporteCitaRepository.añadirReporteCita(descripcion, idCita);
        }
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";

    }

    //consultar si se puede sacar idReceta
    @PostMapping("/pacientesatendidos/verhistorial/vercita/guardarreceta")
    @Transactional
    public String guardarReceta(RedirectAttributes redirectAttributes,
                                @RequestParam("medicamento") String medicamento ,
                                @RequestParam("dosis") String dosis,
                                @RequestParam("descripcion") String descripcion ,
                                //@RequestParam("idReceta") int idReceta,
                                @RequestParam("id") int idCita){

        recetaMedicaRepository.agregarReceta(medicamento,dosis,descripcion,idCita);
        redirectAttributes.addFlashAttribute("msg","Receta Agregada");
        redirectAttributes.addAttribute("id",idCita);
        //redirectAttributes.addAttribute("idReceta",idReceta);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";

    }

    @Transactional
    @GetMapping(value = "/pacientesatendidos/verhistorial/vercita/borrarreceta")
    public String borrarReceta(@RequestParam("idR") Integer idReceta, RedirectAttributes redirectAttributes){

        RecetaMedica receta = recetaMedicaRepository.buscarRecetaMedicaPorID(idReceta);
        Integer idCita = receta.getCita().getIdcita();
        recetaMedicaRepository.borrarReceta(idReceta);
        redirectAttributes.addFlashAttribute("msg2","Receta Borrada");
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";
        }
    @GetMapping("/pacientesatendidos/verhistorial/vercita/boletaMedicamento")
    public String verBoletaFarmacia(Model model,
                                    @RequestParam("idCita") int idCita,
                                    @RequestParam("idPaciente") int idPaciente ){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        List<RecetaMedica>  receta = recetaMedicaRepository.recetaMedicaPorCita(idCita);
        model.addAttribute("receta", receta);
        Paciente paciente = pacienteRepository.buscarPacientePorID(idPaciente);
        model.addAttribute("paciente", paciente);

        return "doctor/boletaFarmacia";
        }

    @GetMapping("/pacientesatendidos/verhistorial/vercita/boletaMedicamentoDelivery")
    public String verBoletaDelivery(Model model,
                                    @RequestParam("idCita") int idCita,
                                    @RequestParam("idPaciente") int idPaciente ){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        List<RecetaMedica>  receta = recetaMedicaRepository.recetaMedicaPorCita(idCita);
        model.addAttribute("receta", receta);
        Paciente paciente = pacienteRepository.buscarPacientePorID(idPaciente);
        model.addAttribute("paciente", paciente);

        return "doctor/boletaDelivery";
    }
    @GetMapping("/calendario")
    public String calendarioDoctor(Model model){
        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        List<Eventocalendariodoctor> eventos = eventocalendariodoctorRepository.calendarioPorDoctor(doctor.getIddoctor());
        model.addAttribute("eventos", eventos);
        model.addAttribute("doctor",doctor);
        return "doctor/calendarioDoc";
    }

    @GetMapping("/cuestionario")
    public String cuestionarioDoctor(Model model, @RequestParam("id") int idPaciente){

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            Paciente paciente1 = pacienteRepository.buscarPacientePorID(idPaciente);
            List<Paciente> lista = pacienteRepository.findAll();
            model.addAttribute("paciente", paciente1);

        return "doctor/cuestionarioDoc";
    }

    @PostMapping("/enviocuestionario")
    public String enviarCuestionario(Model model, @RequestParam("pacienteId") int idP,
                                                @RequestParam("docId") int idD,
                                     RedirectAttributes redirectAttributes){
        Cuestionario nuevocuestionario = new Cuestionario();
        //cuestionarioRepository.save(nuevocuestionario);
        redirectAttributes.addFlashAttribute("msg","Cuestionario Enviado");
        return "redirect:/doctor/dashboard";

    }

    @GetMapping("/perfil")
    public String perfilDoctor(Model model) {

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        return "doctor/perfilDoc";
    }

    @GetMapping("/mensajeria")
    public String mensajeriaDoctor(Model model) {


            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            model.addAttribute("listamensajes",mailCorreoRepository.buscarMensajesRecibidosPorID(doctor.getUsuario().getIdusuario()));

        return "doctor/mensajeriaDoc";
    }

    @GetMapping("/mensajeria/vermensaje")
    public String verMensajeDoctor(Model model, @RequestParam("idM") int idMensaje) {

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            model.addAttribute("mensaje",mailCorreoRepository.buscarMensajePorID(idMensaje));

        return "doctor/verMensajeDoc";
    }

    @GetMapping("/mensajeria/respondermensaje")
    public String responderMensajeDoctor(Model model, @RequestParam("asunto") String asunto,
                                         @RequestParam("id") int idUsuarioOrigen){

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            model.addAttribute("historialmensajes",mailCorreoRepository.buscarMensajePorAsunto(asunto));
            model.addAttribute("usuariorigen",usuarioRepository.usuarioDestino(idUsuarioOrigen));
            model.addAttribute("asunto",asunto);

        return "doctor/responderMensajeDoc";
}



    @GetMapping("/mensajeria/enviarmensaje")
    public String enviarMensajeDoctor(Model model , @RequestParam("idp") int idUsuarioDestino) {
            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            if (idUsuarioDestino != 0) {
                model.addAttribute("usuariodestino", usuarioRepository.usuarioDestino(idUsuarioDestino));
                return "doctor/enviarMensajeDoc";
            }
            return "doctor/enviarMensajeDoc";
    }

    @PostMapping("/mensajeria/enviarmensaje/envio")
    @Transactional
    //ResponseEntity<Void>
    public String sendEmail(RedirectAttributes redirectAttributes, @RequestParam("correodestino") String correoDestino,
                            @RequestParam("asunto") String asunto, @RequestParam("descripcion") String descripcion,
                            @RequestParam("idusuariodestino") int idUsuarioDestino , @RequestParam("idusuarioorigen") int idUsuarioOrigen) {
        emailService.sendEmail(correoDestino,asunto,descripcion);
        mailCorreoRepository.guardarMensaje(asunto,descripcion,correoDestino,idUsuarioDestino ,idUsuarioOrigen);
        redirectAttributes.addFlashAttribute("msg","Mensaje Enviado");
        return "redirect:/doctor/mensajeria";
        //return ResponseEntity.ok().build();
    }

    @GetMapping("/notificaciones")
    public String notificacionesDoctor(Model model) {
        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        return "doctor/notificacionesDoc";
    }

    @PostMapping("/perfil/editarperfil")
    @Transactional
    public String actualizarPerfilDoctor(RedirectAttributes redirectAttributes,
                                         @RequestParam("idusuario") int idUsuario,@RequestParam("nombre") String nombres,
                                         @RequestParam("apellido") String apellidos, @RequestParam("correo") String correo,
                                         @RequestParam("formacion") String formacion,
                                         @RequestParam("capacitaciones") String capacitaciones){
        usuarioRepository.actualizarPerfilDoctor(nombres,apellidos,correo,idUsuario);
        doctorRepository.actualizarDoctor(formacion, capacitaciones, idUsuario);
        redirectAttributes.addFlashAttribute("msg","Perfil Actualizado");
        return "redirect:/doctor/perfil";
    }

    @GetMapping("/configuraciones")
    public String configuracionDoctor(Model model){

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            List<Sede> lista = sedeRepository.findAll();
            model.addAttribute("listSedes", lista);
        return "doctor/configuracionDoc";
    }

    @PostMapping("/guardarSede")
    public String guardarSede(RedirectAttributes attr, @RequestParam("idsede") int idS,
                              @RequestParam("id") int idD){
        doctorRepository.cambiarSede(idS, idD);
        attr.addAttribute("id", idD);
        attr.addAttribute("iddoctor", idS);
        attr.addFlashAttribute("msg","Sede Actualizada");
        return "redirect:/doctor/configuraciones";
    }

    @PostMapping("/cambiarContra")
    @Transactional
    public String cambiarContraseña(Model model, RedirectAttributes attr,
                                    @RequestParam("contrasena") String contrasena,
                                    @RequestParam("newpassword") String newpassword,
                                    @RequestParam("renewpassword") String renewpassword){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        if(doctor.getUsuario().getContrasena().equals(contrasena)){
            usuarioRepository.changePassword(renewpassword,doctor.getUsuario().getIdusuario());
            attr.addFlashAttribute("psw1", "Contraseña actualizada");
        }else {
            attr.addFlashAttribute("psw2", "La contraseña actual es incorrecta");
            return "redirect:/doctor/perfil";
        }
        return "redirect:/doctor/perfil";
    }


}
