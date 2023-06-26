package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.DTO.InformesMedicos;
import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
/*import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;*/
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jdk.jfr.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.proyectogticsgrupo1.Service.EmailService;

import java.io.File;
import java.io.FileOutputStream;
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
    EspecialidadRepository especialidadRepository;

    @Autowired
    private EmailService emailService;

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

    @Autowired
    NotificacionesRepository notificacionesRepository;

    @Autowired
    EstadoCitaRepository estadoCitaRepository;

    @Autowired
    DatosJsonRepository datosJsonRepository;

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
                    if(LocalTime.now().isAfter(cita.getHorainicio()) && LocalTime.now().isBefore(cita.getHorafinal())) {
                        citaRepository.actualizarEstadoCita(4,cita.getIdcita());
                        pacienteRepository.actualizarEstadoPaciente(5,paciente1.getIdpaciente());
                    }else if(LocalTime.now().isAfter(cita.getHorafinal())){
                        //si es verdad , entonces se actualiza el estado de la cita a "finalizada"
                        citaRepository.actualizarEstadoCita(6,cita.getIdcita());
                        pacienteRepository.actualizarEstadoPaciente(3,paciente1.getIdpaciente());
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
                                @RequestParam(name="idReceta", defaultValue = "0") int idReceta,
                                @RequestParam(name="msg6", defaultValue = "") String msg,
                                @RequestParam(name="numcita" ,defaultValue = "0") int numcita){


            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            Cita cita = citaRepository.buscarCitaPorId(idCita);
            model.addAttribute("cita", cita);
            model.addAttribute("numcita",numcita);
            model.addAttribute("estadoscita",estadoCitaRepository.findAll());
            model.addAttribute("recetamedica", recetaMedicaRepository.buscarRecetaMedicaPorCita(idCita, idReceta));
            model.addAttribute("msg6",msg);
        return "doctor/verCita";
    }

    @PostMapping("/pacientesatendidos/verhistorial/vercita/actualizarestadocita")
    @Transactional
    public String actualizarEstadoCita(@RequestParam("id") int idCita,
                                @RequestParam("idestadocita") int idestadocita,
                                RedirectAttributes redirectAttributes){


        citaRepository.actualizarEstadoCita(idestadocita,idCita);
        if(idestadocita==3){
            redirectAttributes.addFlashAttribute("msg7","Cita en Espera");
        }else if(idestadocita== 4){
                redirectAttributes.addFlashAttribute("msg7","Cita Iniciada");
        }else if(idestadocita== 6){
            redirectAttributes.addFlashAttribute("msg7","Cita Finalizada");
        }
        redirectAttributes.addAttribute("id",idCita);
        return "redirect:/doctor/pacientesatendidos/verhistorial/vercita";
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
        model.addAttribute("informesmedicos",informesMedicos);
        model.addAttribute(datosJsonRepository);
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
        Integer idDatosJson = datosJsonRepository.idDatosJson(informeId,idCita);
        if(idDatosJson != null){
            System.out.println(idDatosJson);
            //model.addAttribute("informelleno", datosJsonRepository.informeMedicoLlenado(idDatosJson));
            model.addAttribute("informelleno",datosJsonRepository.modeloJsonLlenado(idDatosJson));
            model.addAttribute("idatosjson",idDatosJson);
        }
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

    @GetMapping("/pacientesatendidos/verhistorial/vercita/anadir")
    public String anadirReceta(Model model, @RequestParam("id") int idCita){
        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        Cita cita = citaRepository.buscarCitaPorId(idCita);
        model.addAttribute("cita", cita);
        return "doctor/anadirReceta";
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
        //void notificarCreacion(Integer iddestino,String contenido, String titulo);
        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        String titulo = "Delivery de Medicamentos Confirmado";
        String contenido = "Estimado Doctor(a): "+doctor.getUsuario().getApellidos()+" se confirmó el delivery de medicamentos para el paciente asignado";
        notificacionesRepository.notificarCreacion(usuarioDoctor.getIdusuario(),contenido,titulo);
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
    @Transactional
    @GetMapping(value = "/calendario/borrarevento")
    public String borrarEvento(Model model,@RequestParam("idEvento") Integer idEvento,
                               RedirectAttributes redirectAttributes){
        Optional<Eventocalendariodoctor> optional = eventocalendariodoctorRepository.findById(idEvento);
        //Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
       // Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        if (optional.isPresent()) {
            eventocalendariodoctorRepository.deleteById(idEvento);
        }
       // model.addAttribute("doctor", doctor);
        //Eventocalendariodoctor evento = eventocalendariodoctorRepository.buscarEventoPorID(idEvento);
        //LocalDate fecha = evento.getFecha();
        //model.addAttribute("fecha", fecha);

        redirectAttributes.addFlashAttribute("msg2","Evento Borrado");
        //return "redirect:/doctor/calendario/agregar";
        return "redirect:/doctor/calendario";
    }

    @PostMapping(value = "/calendario/agregar")
    public String agregarEvento(Model model, @RequestParam ("iddoctor") int iddoctor,
                                @RequestParam("fecha") LocalDate fecha){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        List<Eventocalendariodoctor> eventos = eventocalendariodoctorRepository.calendarioPorDoctorPorFecha(doctor.getIddoctor(), fecha);
        model.addAttribute("eventos", eventos);
        model.addAttribute("horasDisponiblesInicioTurnoM", eventocalendariodoctorRepository.horasDeCitasInicio(iddoctor, fecha));
        model.addAttribute("horasDisponiblesFinalTurnoM", eventocalendariodoctorRepository.horasDeCitasFinal(iddoctor, fecha));
        model.addAttribute("horasDisponiblesInicioRefrigerio", eventocalendariodoctorRepository.horasDeCitasInicioRefrigerio(iddoctor, fecha));
        model.addAttribute("horasDisponiblesFinalRefrigerio", eventocalendariodoctorRepository.horasDeCitasFinalRefrigerio(iddoctor, fecha));
        model.addAttribute("horasDisponiblesInicioTurnoT", eventocalendariodoctorRepository.horasDeCitasInicioTarde(iddoctor, fecha));
        model.addAttribute("horasDisponiblesFinalTurnoT", eventocalendariodoctorRepository.horasDeCitasFinalTarde(iddoctor, fecha));

        model.addAttribute("doctor", doctor);
        model.addAttribute("fecha", fecha);

        return "doctor/anadirCalendario";
    }

    @Transactional
    @PostMapping(value = "/calendario/guardar1")
    public String agregarEvento1(Model model, @RequestParam("fecha") LocalDate fecha ,
                                @RequestParam("horainicio") LocalTime horainicio ,
                                @RequestParam("horafinal") LocalTime horafinal ,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("iddoctor") Integer iddoctor, RedirectAttributes redirectAttributes){
        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor", doctor);
        Integer duracion = 1;
        Integer idtipocalendario = 1;
        redirectAttributes.addFlashAttribute("msg","Evento Añadido");
        eventocalendariodoctorRepository.agregarEventoDoctor(idtipocalendario,fecha, horainicio, horafinal, duracion, descripcion,iddoctor);
        return "redirect:/doctor/calendario";
    }

    @Transactional
    @PostMapping(value = "/calendario/guardar2")
    public String agregarEvento2(Model model, @RequestParam("fecha") LocalDate fecha ,
                                @RequestParam("horainicio") LocalTime horainicio ,
                                @RequestParam("horafinal") LocalTime horafinal ,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("iddoctor") Integer iddoctor,
                                RedirectAttributes redirectAttributes){

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor", doctor);
        Integer duracion = 1;
        Integer idtipocalendario = 2;
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
            model.addAttribute(datosJsonRepository);
            Integer idDatosJsonCuestionario = datosJsonRepository.idDatosJson(cuestionarioMedicoId,idCita);
            if(idDatosJsonCuestionario != null){
                model.addAttribute("iddatosjson",idDatosJsonCuestionario);
                model.addAttribute("cuestionariolleno",datosJsonRepository.modeloJsonLlenado(idDatosJsonCuestionario));
            }

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
        System.out.println("llega al repo de enviar");
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
    public String enviarMensajeDoctor(Model model , @RequestParam("idu") int idUsuarioDestino) {
            Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
            Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
            model.addAttribute("doctor",doctor);
            if (idUsuarioDestino != 0) {
                Usuario usuarioDestino = usuarioRepository.usuarioDestino(idUsuarioDestino);
                model.addAttribute("usuariodestino", usuarioDestino);
                return "doctor/enviarMensajeDoc";
            }
            return "doctor/enviarMensajeDoc";
    }

    @GetMapping("/mensajeria/enviarmensaje/examenes")
    public String enviarMensajeDeExamenes(Model model , @RequestParam("idp") int idUsuarioDestino) {
        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        if (idUsuarioDestino != 0) {
            Usuario usuarioDestino = usuarioRepository.usuarioDestino(idUsuarioDestino);
            model.addAttribute("usuariodestino", usuarioDestino);
            Optional<Paciente> optPaciente = Optional.ofNullable(pacienteRepository.buscarPacientePorIdUsuario(idUsuarioDestino));
            if(optPaciente.isPresent()){
                Integer idpaciente = optPaciente.get().getIdpaciente();
                model.addAttribute("idpaciente",idpaciente);
                model.addAttribute("especialidades",especialidadRepository.findAll());
            }
            return "doctor/enviarMensajeExamenesDoc";
        }
        return "doctor/enviarMensajeExamenesDoc";
    }

    @GetMapping("/mensajeria/veradministrativos")
    public String enviarMensaje2Doctor(Model model) {

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        model.addAttribute("administrativos",usuarioRepository.listaAdministrativos(usuarioDoctor.getSede().getIdsede(),usuarioDoctor.getEspecialidad().getIdespecialidad()));

        return "doctor/veradministrativos";
    }

    @PostMapping("/mensajeria/enviarmensaje/envio")
    @Transactional
    //ResponseEntity<Void>
    public String sendEmailmvc(RedirectAttributes redirectAttributes, @RequestParam("correodestino") String correoDestino,
                            @RequestParam("asunto") String asunto, @RequestParam("descripcion") String descripcion,
                            @RequestParam("idusuariodestino") int idUsuarioDestino , @RequestParam("idusuarioorigen") int idUsuarioOrigen) {


        //String titulo = "Estimado Paciente , el doctor(a) requiere que se haga unos examenes de rayos x";
        //notificacionesRepository.notificarCreacion(idUsuarioDestino,descripcion,titulo);
        emailService.sendEmail(correoDestino,asunto,descripcion);
        mailCorreoRepository.guardarMensaje(asunto,descripcion,correoDestino,idUsuarioDestino ,idUsuarioOrigen);
        redirectAttributes.addFlashAttribute("msg","Mensaje Enviado");
        return "redirect:/doctor/mensajeria";
        //return ResponseEntity.ok().build();
    }

    @PostMapping("/mensajeria/enviarmensaje/examenes/envio")
    @Transactional
    public String sendEmail2(RedirectAttributes redirectAttributes, @RequestParam("correodestino") String correoDestino,
                            @RequestParam("asunto") String asunto, @RequestParam("descripcion") String descripcion,
                            @RequestParam("idusuariodestino") int idUsuarioDestino , @RequestParam("idusuarioorigen") int idUsuarioOrigen ,
                            @RequestParam("idespecialidad") int idespecialidad) {

        //Optional<Paciente> optPaciente = Optional.ofNullable(pacienteRepository.buscarPacientePorIdUsuario(idUsuarioDestino));
        Paciente paciente1 = pacienteRepository.buscarPacientePorIdUsuario(idUsuarioDestino);
        String especialidades;
        //aañ
        if(paciente1.getEspecialidadesPendientes().equals(null)){
            especialidades = Integer.toString(idespecialidad);
        }else{
            especialidades = paciente1.getEspecialidadesPendientes() + "," + idespecialidad;
        }
        System.out.println("pacientesid " + paciente1.getIdpaciente());
        pacienteRepository.modificarEspecialidadesPendientes(especialidades, paciente1.getIdpaciente());
        pacienteRepository.actualizarEstadoPaciente(6,paciente1.getIdpaciente());
        notificacionesRepository.notificarCreacion(idUsuarioDestino,descripcion,"Requerimiento de Examenes");
        emailService.sendEmail(correoDestino,asunto,descripcion);
        //mailCorreoRepository.guardarMensaje(asunto,descripcion,correoDestino,idUsuarioDestino ,idUsuarioOrigen);
        redirectAttributes.addFlashAttribute("msg","Mensaje Enviado");
        return "redirect:/doctor/mensajeria";
        //return ResponseEntity.ok().build();
    }


    @PostMapping("/cuestionario_envio")
    @Transactional
    //ResponseEntity<Void>
    public String cuestionarioEnvio(RedirectAttributes redirectAttributes,
                            @RequestParam("id_cita") int id_cita , @RequestParam("id_modelo") int id_modelo,
                                    @RequestParam("id_usuario_paciente") int id_paciente) {

        System.out.println("llega al repo de envio");
        modeloJsonRepository.agregarCuestionarioAPaciente(id_modelo,id_paciente,id_cita);
        Paciente paciente1 = pacienteRepository.buscarPacientePorID(id_paciente);
        notificacionesRepository.notificarCreacion(paciente1.getUsuario().getIdusuario(),"Estimado Paciente, recuerde llenar el cuestionario enviado por el doctor","Cuestionario Pendiente");
        emailService.sendEmail(paciente1.getUsuario().getCorreo(),"Cuestionario Pendiente","Estimado Paciente, recuerde llenar el cuestionario enviado por el doctor antes de su cita");


        redirectAttributes.addFlashAttribute("msg","Cuestionario enviado");
        return "redirect:/doctor/pacientesatendidos";
        //return ResponseEntity.ok().build();
    }

    @GetMapping("/notificaciones")
    public String notificacionesDoctor(Model model) {
        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        model.addAttribute("notificaciones",notificacionesRepository.notificacionesPorUsuario(doctor.getUsuario().getIdusuario()));
        return "doctor/notificacionesDoc";
    }

    @GetMapping("/perfil")
    public String perfilDoctor(Model model) {

        Usuario usuarioDoctor = (Usuario) session.getAttribute("usuario");
        Doctor doctor = doctorRepository.buscarDoctorPorIdUsuario(usuarioDoctor.getIdusuario());
        model.addAttribute("doctor",doctor);
        return "doctor/perfilDoc";
    }

    @PostMapping("/perfil/editarperfil")
    @Transactional
    public String actualizarPerfilDoctor(RedirectAttributes redirectAttributes,
                                         @RequestParam("idusuario") int idUsuario,
                                         @RequestParam("nombre") String nombres,
                                         @RequestParam("apellido") String apellidos,
                                         @RequestParam("formacion") String formacion,
                                         @RequestParam("capacitaciones") String capacitaciones){
        usuarioRepository.actualizarPerfilDoctor(nombres,apellidos,idUsuario);
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
        Doctor doctor = doctorRepository.buscarDoctorPorId(idD);
        Sede sede = sedeRepository.buscarSedePorId(idS);
        String content="Estimado Doctor(a): "+doctor.getUsuario().getNombres()+" "+doctor.getUsuario().getApellidos()+ " se realizó su cambio de sede a" +sede.getNombre()+ "";
        String titulo="Cambio de Sede a: "+sede.getNombre()+ "";
        //notificacionesRepository.notificarCreacion(doctor.getUsuario().getIdusuario(),content,titulo);
        //attr.addAttribute("id", idD);
        //attr.addAttribute("iddoctor", idS);
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



/*
    @PostMapping("/guardarImagen")
    public String guardarImagenEvento(@RequestParam("file") MultipartFile file, @RequestParam("id") int id, RedirectAttributes attr) {
        System.out.println("llega a guardar");
        StringBuilder fileNames = new StringBuilder();
        String nombreArchivo= "foto-usuario-" + id;
        System.out.println("nombre en guardar"+nombreArchivo);
        uploadObject(file,nombreArchivo, "gigacontrol", "l5-20203368-2023-1-gtics");
        return "redirect:/doctor/perfil";
    }

    public static void uploadObject
            (MultipartFile multipartFile, String fileName, String projectId, String gcpBucketId) {
        try {
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
//            RandomString id = new RandomString(6, ThreadLocalRandom.current());
            Blob blob = bucket.create("proyecto" + "/" + fileName + checkFileExtension(fileName), fileData);

            if (blob != null) {
                System.out.println("errro?");
                LOGGER.debug("File successfully uploaded to GCS");
                return new FileDto(blob.getName(), blob.getMediaLink());
            }
        } catch (Exception e) {
            System.out.println("errro?2");
//            LOGGER.error("An error occurred while uploading data. Exception: ", e);
            throw new RuntimeException("An error occurred while storing data to GCS");
        }
    }

    private static File convertFile(MultipartFile file) {

        try {
            if (file.getOriginalFilename() == null) {
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            return convertedFile;
        } catch (Exception e) {
            throw new RuntimeException("An error has occurred while converting the file");
        }
    }

    private static String checkFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3"};

            for (String extension : extensionList) {
                if (fileName.endsWith(extension)) {
//                    LOGGER.debug("Accepted file type : {}", extension);
                    return extension;
                }
            }
        }
        return ".jpeg";
    }
*/

}
