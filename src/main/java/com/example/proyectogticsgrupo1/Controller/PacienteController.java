package com.example.proyectogticsgrupo1.Controller;

import com.example.proyectogticsgrupo1.Entity.*;
import com.example.proyectogticsgrupo1.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/paciente")
public class PacienteController {

    final SedeRepository sedeRepository;
    final EspecialidadRepository especialidadRepository;
    final DoctorRepository doctorRepository;
    final UserRepository userRepository;
    final PacienteRepository pacienteRepository;
    final TipoCitaRepository tipoCitaRepository;
    final CitaRepository citaRepository;
    final EventocalendariodoctorRepository eventocalendariodoctorRepository;

    public PacienteController(SedeRepository sedeRepository, EspecialidadRepository especialidadRepository, DoctorRepository doctorRepository, UserRepository userRepository, PacienteRepository pacienteRepository, TipoCitaRepository tipoCitaRepository, CitaRepository citaRepository, EventocalendariodoctorRepository eventocalendariodoctorRepository) {
        this.sedeRepository = sedeRepository;
        this.especialidadRepository = especialidadRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.pacienteRepository = pacienteRepository;
        this.tipoCitaRepository = tipoCitaRepository;
        this.citaRepository = citaRepository;
        this.eventocalendariodoctorRepository = eventocalendariodoctorRepository;
    }

    @GetMapping(value = "/")
    public String paciente(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        model.addAttribute("docs", doctorRepository.findAll());
        model.addAttribute("sedes", sedeRepository.findAll());
        return "paciente/index";
    }

    @GetMapping(value = "/perfilDoctor")
    public String perfilDoc(RedirectAttributes redirectAttributes, @RequestParam("iddoc") Integer iddoc, Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(iddoc);
        if(optionalDoctor.isPresent()){
            Doctor doctor=optionalDoctor.get();
            model.addAttribute("doc",doctor);
            return "paciente/perfilDoctor";
        }
        else {
            return "redirect:/paciente/";
        }

    }
    @GetMapping(value = "/selecTipoCita")
    public String selecTipoCita(Model model,@RequestParam("iddoc") Integer id){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        model.addAttribute("doc", doctorRepository.findById(id).get());
        return "paciente/tipocita";
    }
    @GetMapping(value = "/reservar2")
    public String selectDate(Model model, @RequestParam("iddoc") Integer id){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        //Eventocalendariodoctor eventocalendariodoctor = eventocalendariodoctorRepository.calendarioPorDoctor(id);
        model.addAttribute("calendario", eventocalendariodoctorRepository.calendarioPorDoctor(id));
        return "paciente/reservar2";
    }

    @GetMapping(value = "/pagos")
    public String pagosView(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/pagos";
    }

    @GetMapping(value = "/perfil")
    public String perfilPaciente(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/perfil";
    }

    @GetMapping(value = "/agendarCita")
    public String agendarCita(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        List<Sede> listsede = sedeRepository.findAll();
        List<Especialidad> listespecialidad = especialidadRepository.findAll();
        model.addAttribute("sedesparacitas", listsede);
        model.addAttribute("especialidades", listespecialidad);
        model.addAttribute("pacientelog",paciente);
        model.addAttribute("doctores",doctorRepository.findAll());
        model.addAttribute("tipocita",tipoCitaRepository.findAll());
        return "paciente/agendarCita";
    }
    @GetMapping(value = "/historialCitas")
    public String historialCitas(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("listcitas", citaRepository.citaPorPaciente(paciente.getIdpaciente()));
        model.addAttribute("pacientelog",paciente);
        return "paciente/historialCitas";
    }

    @GetMapping(value = "/calendarioMensual")
    public String calendarioMensual(Model model){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        model.addAttribute("pacientelog",paciente);
        return "paciente/calendarioMensual";
    }


    @PostMapping(value = "/alergia")
    @Transactional
    public String modAlergia(@RequestParam("alergias") String alergia, @RequestParam("idpaciente") Integer id, RedirectAttributes redirectAttributes){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        String alergias = paciente.getAlergias();
        if(alergias.contains(alergia)){
            redirectAttributes.addFlashAttribute("msg1","Esa alergia ya se encuentra registrada");
        }else {
            String alg = paciente.getAlergias()+","+alergia;
            pacienteRepository.modificarAlergia(alg, id);
            redirectAttributes.addFlashAttribute("msg2","Alergia agregada correctamente");
        }
        return "redirect:/paciente/perfil";
    }


    @PostMapping(value = "/changepassword")
    @Transactional
    public String changePassword(@RequestParam("contrasena") String contrasena, @RequestParam("newpassword") String newpassword, @RequestParam("renewpassword") String renewpassword, RedirectAttributes redirectAttributes){
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(1);
        Paciente paciente =  optionalPaciente.get();
        if(paciente.getUsuario().getContrasena().equals(contrasena)){
            if(newpassword.equals(renewpassword)){
                userRepository.changePassword(renewpassword,paciente.getUsuario().getIdusuario());
                redirectAttributes.addFlashAttribute("psw1", "Contraseña actualizada");
            }
            else {
                redirectAttributes.addFlashAttribute("psw2", "La contraseñas ingresadas no coinciden");
            }
        }else {
            redirectAttributes.addFlashAttribute("psw2", "La contraseña es incorrecta");
        }
        return "redirect:/paciente/perfil";
    }

    @PostMapping(value = "/pruebascita")
    @Transactional
    public String pruebascita(@RequestParam("idsede") Integer idsede,
                              @RequestParam("especialidadid") Integer idesp,
                              @RequestParam("iddoctor") Integer iddoctor,
                              @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha ,
                              @RequestParam("hora")LocalTime hora,
                              @RequestParam("idseguro") Integer idseguro,
                              @RequestParam("idtipocita") Integer idtipocita, RedirectAttributes redirectAttributes){
        if(idsede==doctorRepository.findById(iddoctor).get().getSede().getIdsede()){
            if(idesp==doctorRepository.findById(iddoctor).get().getSede().getIdsede()){
                if(eventocalendariodoctorRepository.calendarioPorDoctor(iddoctor).getFecha().toString().equals(fecha.toString())){
                    if(eventocalendariodoctorRepository.calendarioPorDoctor(iddoctor).getHorainicio()==hora){
                        citaRepository.agengedarcita(especialidadRepository.getCosto(idesp), idsede, 1, idesp, iddoctor, fecha, hora, hora.plusHours(1),60, idtipocita, idseguro, 1);
                        System.out.println("guardo?");
                        return "redirect:/paciente/";
                    }
                    else {
                        redirectAttributes.addFlashAttribute("msg", "La hora no es valida");
                        return "redirect:/paciente/agendarCita";
                    }
                }
                else {
                    System.out.println(fecha);
                    System.out.println(eventocalendariodoctorRepository.calendarioPorDoctor(iddoctor).getFecha());
                    String a = fecha.toString();
                    String  b = eventocalendariodoctorRepository.calendarioPorDoctor(iddoctor).getFecha().toString();
                    System.out.println(a);
                    System.out.println(b);
                    System.out.println(a.equals(b));
                    redirectAttributes.addFlashAttribute("msg", "La fecha escogida no es valida");
                    return "redirect:/paciente/agendarCita";
                }
            }
            else{
                redirectAttributes.addFlashAttribute("msg", "El doctor no pertenece a esa especialidad");
                return "redirect:/paciente/agendarCita";
            }
        }else {
            redirectAttributes.addFlashAttribute("msg", "El doctor debe pertenecer a la sede");
            return "redirect:/paciente/agendarCita";
        }
    }

}
