package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.DTO.InformesMedicos;
import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    TablaDatosLlenosRepository tablaDatosLlenosRepository;

    @Autowired
    TipohoracalendariodoctorRepository tipohoracalendariodoctorRepository;

    @Autowired
    ModeloJsonRepository modeloJsonRepository;

    @Autowired
    TablaTitulosInputsRepository tablaTitulosInputsRepository;

    public DoctorController(CitaRepository citaRepository, DoctorRepository doctorRepository, PacienteRepository pacienteRepository,
                            RecetaMedicaRepository recetaMedicaRepository, ReporteCitaRepository reporteCitaRepository,UsuarioRepository usuarioRepository,
                            BitacoraDeDiagnosticoRepository bitacoraDeDiagnosticoRepository,
                            TipohoracalendariodoctorRepository tipohoracalendariodoctorRepository,
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
        this.tipohoracalendariodoctorRepository = tipohoracalendariodoctorRepository ;
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
    @GetMapping("/dashboard/info")
    public String infoDashboard(Model model, @RequestParam("id") int idPaciente) {

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        Paciente paciente = pacienteRepository.buscarPacientePorID(idPaciente);
        model.addAttribute("paciente", paciente);
        List<Cita> citasAgendadas1 = citaRepository.buscarCitasAgendadasDoctor(doctor.getIddoctor());

        model.addAttribute("citasAgendadas",citasAgendadas1);
        return "doctor/infoDashboard";
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
            List<Cita> listaCitasPaciente = citaRepository.citasPorPaciente(idPaciente,doctor.getIddoctor());
            for (Cita cita:listaCitasPaciente){
                //primero se verifica si la fecha de la cita coincide con el dia actual
                if(cita.getFecha().equals(LocalDate.now())){
                    //si coincide ,entonces vemos si su hora final es menor a la hora actual
                    if(LocalTime.now().isAfter(cita.getHorafinal())){
                        //si es verdad , entonces se actualiza el estado de la cita a "finalizada"
                        citaRepository.actualizarEstadoCita(6,cita.getIdcita());
                    }
                }
            }
            model.addAttribute("citaspaciente", listaCitasPaciente);
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
            Cita cita = citaRepository.buscarCitaPorId(idCita);
            model.addAttribute("cita", cita);
            model.addAttribute("recetamedica", recetaMedicaRepository.buscarRecetaMedicaPorCita(idCita, idReceta));
        return "doctor/verCita";
    }

    @GetMapping("/pacientesatendidos/verhistorial/vercita/verinformesmedico")
    public String verInformeMedico(Model model, @RequestParam("id") int idCita) {

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        Cita cita = citaRepository.buscarCitaPorId(idCita);
        model.addAttribute("cita", cita);
        //obtenemos el id del modelo del informe y luego se enviarán los datos desde la vista para llenar en la tabla "datos_json"
        List<InformesMedicos> informesMedicos = modeloJsonRepository.obtenerInformesMedico(doctor.getEspecialidad().getIdespecialidad());
        //model.addAttribute("informemedico",informe);
        model.addAttribute("informesmedicos",informesMedicos);
        return "doctor/verInformesMedico";
    }

    @GetMapping("/pacientesatendidos/verhistorial/vercita/verinformesmedico/llenarinforme")
    public String verInformeMedico(Model model, @RequestParam("id") int idCita,@RequestParam("idinforme") int informeId) {

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        Cita cita = citaRepository.buscarCitaPorId(idCita);
        model.addAttribute("cita", cita);
        //obtenemos el id del modelo del informe y luego se enviarán los datos desde la vista para llenar en la tabla "datos_json"
        //int informeId = modeloJsonRepository.informeMedicoId(doctor.getEspecialidad().getIdespecialidad());
        //model.addAttribute("informemedico",informe);
        model.addAttribute("listapreguntasinforme",modeloJsonRepository.listarPreguntasxPlantilla(informeId));
        model.addAttribute("idinforme",informeId);
        return "doctor/llenarInforme";
    }


    @Transactional
    @PostMapping(value = "/pacientesatendidos/verhistorial/vercita/verinformesmedico/llenarinforme/guardar")
    public String modificarPlantilla(Model model, @RequestParam("valores") List<String> valores,
                                     RedirectAttributes redirectAttributes){
        System.out.println("llega al repo de modificar");
        System.out.println(valores);


        //id modelo
        String primerValor_id = valores.get(0);

        System.out.println(primerValor_id);

        //se remueve el id modelo a la lista valores
        valores.remove(0);

        System.out.println(valores);

        valores.remove(0);

        System.out.println(valores);

        //parseo del id modelo
        int primerValorInt_id = Integer.parseInt(String.valueOf(primerValor_id));


        //Obtenemos el modelo json (en este caso informe)
        ModeloJsonEntity EncontrarModelo = modeloJsonRepository.buscarModeloEdit(primerValorInt_id);

        //Luego obtenemos el nombre de plantilla,especialidad,tipo de usuario , etc para
        //llenar en datos_json
        String nbr_plantilla = EncontrarModelo.getNombrePlantilla();
        int id_especialidad = EncontrarModelo.getEspecialidad().getIdespecialidad();
        int id_tipo_usuario = EncontrarModelo.getTipodeusuario().getIdtipodeusuario();
        Byte flg_formulario = EncontrarModelo.getFormulario();
        Byte flg_cuestionario = EncontrarModelo.getCuestionario();

//        if(EncontrarModelo.getCuestionario() ==null){


        Byte flg_informe = EncontrarModelo.getInforme();

        int tamano_valores= valores.size();
        String idusuario = valores.get(tamano_valores-2);
        String idcita = valores.get(tamano_valores-1);

        int idCita = Integer.parseInt(String.valueOf(idcita));

        int idUsuario = Integer.parseInt(String.valueOf(idusuario));



        System.out.println("nbr_plantilla="+nbr_plantilla);
        System.out.println("id_especialidad="+id_especialidad);
        System.out.println("id_tipo_usuario="+id_tipo_usuario);
        System.out.println("flg_formulario="+flg_formulario);
        System.out.println("flg_cuestionario="+flg_cuestionario);
        System.out.println("flg_informe="+flg_informe);


        /*modeloJsonRepository.borrarPlantillas(primerValorInt_id);


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



//        modeloJsonRepository.borrarPlantillas(id_de_modelo_plantilla); */

        for (String elemento : valores) {
            tablaDatosLlenosRepository.agregarDatosDeInput(elemento);
        }

//        tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo,nombreplantilla,id_usuario,id_modelo,id_cita);

//
//
//       tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo,nombreplantilla,id_usuario,id_modelo,id_cita);
        tablaDatosLlenosRepository.llenadoDeInformeMedico(nbr_plantilla,idUsuario,primerValorInt_id,idCita);
        //para llenar en datos_json

        //jalar para borrar
        tablaDatosLlenosRepository.BorrarDatosDeInput();

        redirectAttributes.addAttribute("id",idCita);
        return "redirect: /doctor/pacientesatendidos/verhistorial/vercita ";

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
                                    @RequestParam("idPaciente") int idPaciente,
                                    RedirectAttributes redirectAttributes){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        List<RecetaMedica>  receta = recetaMedicaRepository.recetaMedicaPorCita(idCita);
        model.addAttribute("receta", receta);
        Paciente paciente = pacienteRepository.buscarPacientePorID(idPaciente);
        model.addAttribute("paciente", paciente);
        Cita cita = citaRepository.buscarCitaPorId(idCita);
        model.addAttribute("cita", cita);
        redirectAttributes.addFlashAttribute("msg","Pedido programado para ser enviado");

        return "doctor/boletaDelivery";
    }

    @Transactional
    @PostMapping("/pacientesatendidos/verhistorial/vercita/boletaMedicamentoDelivery/confirmar")
    public String confirmarEnvio(@RequestParam("id") int idcita,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("msg5","Delivery Confirmado");
        redirectAttributes.addAttribute("id",idcita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";
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

    @PostMapping(value = "/calendario/agregar")
    public String agregarEvento(Model model, @RequestParam ("iddoctor") int iddoctor, @RequestParam("fecha") LocalDate fecha){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Convertir la cadena de caracteres a LocalDate
        //LocalDate date = LocalDate.parse(dateString, formatter);
        model.addAttribute("horasDisponiblesInicio", eventocalendariodoctorRepository.horasDeCitasInicio(iddoctor, fecha));
        model.addAttribute("horasDisponiblesFinal", eventocalendariodoctorRepository.horasDeCitasFinal(iddoctor, fecha));
        model.addAttribute("doctor", doctor);
        model.addAttribute("fecha", fecha);
        model.addAttribute("tipocita", tipohoracalendariodoctorRepository.findAll());
        return "doctor/anadirCalendario";
    }

    @Transactional
    @PostMapping(value = "/calendario/guardar")
    public String agregarEvento(Model model, @RequestParam("fecha") LocalDate fecha ,
                                @RequestParam("horainicio") LocalTime horainicio ,
                                @RequestParam("horafinal") LocalTime horafinal ,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("idtipocalendario") Integer idtipocalendario,
                                @RequestParam("iddoctor") Integer iddoctor,
                                RedirectAttributes redirectAttributes){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        Integer duracion = 1;
        eventocalendariodoctorRepository.agregarEventoDoctor(idtipocalendario,fecha, horainicio, horafinal, duracion, descripcion,iddoctor);
        redirectAttributes.addFlashAttribute("msg","Evento Añadido");
        return "redirect:/doctor/calendario";
    }

    @GetMapping("/cuestionario")
    public String cuestionarioDoctor(Model model, @RequestParam("id") int idPaciente , @RequestParam("idcita") int idCita){

            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            Paciente paciente1 = pacienteRepository.buscarPacientePorID(idPaciente);
            List<Paciente> lista = pacienteRepository.findAll();
            model.addAttribute("paciente", paciente1);
            int cuestionarioMedicoId = modeloJsonRepository.cuestionarioMedicoId(doctor.getEspecialidad().getIdespecialidad());
            //model.addAttribute("informemedico",informe);
            model.addAttribute("listapreguntascuestionario",modeloJsonRepository.listarPreguntasxPlantilla(cuestionarioMedicoId));
            model.addAttribute("idcuestionario",cuestionarioMedicoId);
            model.addAttribute("idcita",idCita);

        return "doctor/cuestionarioDoc";
    }

    /*@PostMapping("/enviocuestionario")
    @Transactional
    public String enviarCuestionario(Model model, @RequestParam("mostrar_automatico") int mostrar_automatico,
                                     @RequestParam("idespecialidad") Integer idespecialidad,
                                     @RequestParam("correodestino") String correodestino,
                                     RedirectAttributes redirectAttributes){
        modeloJsonRepository.mostrarCuestionarioAutomatico(mostrar_automatico,idespecialidad);
        String asunto = "Envio de Cuestionario";
        String descripcion = "Estimado Paciente se le asignó un cuestionario para llenar antes de su siguiente cita, gracias";
        emailService.sendEmail(correodestino,asunto,descripcion);
        redirectAttributes.addFlashAttribute("msg","Cuestionario Enviado");
        return "redirect:/doctor/dashboard";
    } */
    @Transactional
    @PostMapping(value = "/cuestionario/enviarcuestionario")
    public String enviarCuestionario(Model model, @RequestParam("valores") List<String> valores,
                                     RedirectAttributes redirectAttributes){
        System.out.println("llega al repo de modificar");
        System.out.println(valores);


        //id modelo
        String primerValor_id = valores.get(0);

        System.out.println(primerValor_id);

        //se remueve el id modelo a la lista valores
        valores.remove(0);

        System.out.println(valores);

        valores.remove(0);

        System.out.println(valores);

        //parseo del id modelo
        int primerValorInt_id = Integer.parseInt(String.valueOf(primerValor_id));


        //Obtenemos el modelo json (en este caso informe)
        ModeloJsonEntity EncontrarModelo = modeloJsonRepository.buscarModeloEdit(primerValorInt_id);

        //Luego obtenemos el nombre de plantilla,especialidad,tipo de usuario , etc para
        //llenar en datos_json
        String nbr_plantilla = EncontrarModelo.getNombrePlantilla();
        int id_especialidad = EncontrarModelo.getEspecialidad().getIdespecialidad();
        int id_tipo_usuario = EncontrarModelo.getTipodeusuario().getIdtipodeusuario();
        Byte flg_formulario = EncontrarModelo.getFormulario();
        Byte flg_cuestionario = EncontrarModelo.getCuestionario();

//        if(EncontrarModelo.getCuestionario() ==null){


        Byte flg_informe = EncontrarModelo.getInforme();

        int tamano_valores= valores.size();
        String idusuario = valores.get(tamano_valores-2);
        String idcita = valores.get(tamano_valores-1);

        int idCita = Integer.parseInt(String.valueOf(idcita));

        int idUsuario = Integer.parseInt(String.valueOf(idusuario));



        System.out.println("nbr_plantilla="+nbr_plantilla);
        System.out.println("id_especialidad="+id_especialidad);
        System.out.println("id_tipo_usuario="+id_tipo_usuario);
        System.out.println("flg_formulario="+flg_formulario);
        System.out.println("flg_cuestionario="+flg_cuestionario);
        System.out.println("flg_informe="+flg_informe);


        /*modeloJsonRepository.borrarPlantillas(primerValorInt_id);


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



//        modeloJsonRepository.borrarPlantillas(id_de_modelo_plantilla); */

        for (String elemento : valores) {
            tablaDatosLlenosRepository.agregarDatosDeInput(elemento);
        }

//        tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo,nombreplantilla,id_usuario,id_modelo,id_cita);

//
//
//       tablaDatosLlenosRepository.LlenadoDePlantilla(id_registro_nuevo,nombreplantilla,id_usuario,id_modelo,id_cita);
        tablaDatosLlenosRepository.llenadoDeInformeMedico(nbr_plantilla,idUsuario,primerValorInt_id,idCita);
        //para llenar en datos_json

        //jalar para borrar
        tablaDatosLlenosRepository.BorrarDatosDeInput();

        //redirectAttributes.addAttribute("id",idCita);
        return "redirect: /doctor/pacientesatendidos ";

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
        Optional<Paciente> optPaciente = Optional.ofNullable(pacienteRepository.buscarPacientePorIdUsuario(idUsuarioDestino));
        if(optPaciente.isPresent()){
            pacienteRepository.actualizarEstadoPaciente(6,optPaciente.get().getIdpaciente());
        }
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if(passwordEncoder.matches(contrasena, doctor.getUsuario().getContrasena())){
            String hashedNewPassword = passwordEncoder.encode(newpassword);
            userRepository.changePassword(hashedNewPassword,doctor.getUsuario().getIdusuario());
            attr.addFlashAttribute("psw1", "Contraseña actualizada");
        }else {
            attr.addFlashAttribute("psw2", "La contraseña actual es incorrecta");
            return "redirect:/doctor/perfil";
        }
        return "redirect:/doctor/perfil";
    }


}
