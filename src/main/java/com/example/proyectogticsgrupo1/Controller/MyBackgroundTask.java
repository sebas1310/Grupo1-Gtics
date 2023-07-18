package com.example.proyectogticsgrupo1.Controller;
import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.EmailService;
import com.example.proyectogticsgrupo1.Repository.*;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@EnableAsync
@EnableScheduling
@Component
public class MyBackgroundTask {


    @Autowired
    EmailService emailService;
    @Autowired
    CitaRepository citaRepository;

    @Autowired
    NotificacionesRepository notificacionesRepository;

    @Autowired
    BoletaDoctorRepository boletaDoctorRepository;


    @Autowired
    BoletaPacienteRepository boletaPacienteRepository;

    @Autowired
    ModeloXCitaRepository modeloXCitaRepository;

    @Autowired
    DatosJsonRepository datosJsonRepository;

    @Autowired
    EventocalendariodoctorRepository eventocalendariodoctorRepository;

    @Async
    @Scheduled(fixedDelay = 1000*35) // Ejecutar cada 35 segundos
    @Transactional
    public void doBackgroundTask() throws IOException {
        //todas las citas
        System.out.println(citaRepository.citasProxToday().size());
        for (Cita c : citaRepository.citasProxToday()) {
            //System.out.println(c.getHorainicio());
            //System.out.println(c.getHorainicio());
            ///System.out.println(LocalTime.now());
            System.out.println("ahora "+LocalTime.now().minusHours(5));
            System.out.println("cita prox sin pagar a cancelar: "+c.getHorainicio().minusHours(1));
            System.out.println("inicio: " +  c.getHorainicio() +"id :" + c.getIdcita());
            //si la hora actual es igual a la hora de inicio de la cita menos 1, entonces se canelara por falta de pago
            if (c.getHorainicio().minusHours(1).isBefore(LocalTime.now().minusHours(5)) && c.getEstadoCita().getIdestadocita()==1) { //eliminar 1h antes todo
                //System.out.println(LocalTime.now());
                System.out.println(c.getIdcita());
                //se elimina boletas primero
                boletaPacienteRepository.delBoletaPaciente(c.getIdcita());
                boletaDoctorRepository.delBoletaDoctor(c.getIdcita());
                //y de ahi la cita
                Integer idModel = modeloXCitaRepository.idModelxCita(c.getIdcita());
                if(idModel!=null){
                    modeloXCitaRepository.borrarModelxCita(idModel);
                }
                Integer idCuestionario = datosJsonRepository.idCuestionariolleno(c.getPaciente().getUsuario().getIdusuario(),c.getIdcita());
                if(idCuestionario!=null){
                    datosJsonRepository.borrarDatosJson(idCuestionario);
                }
                eventocalendariodoctorRepository.cambiarEstadoCalendario2(c.getDoctor().getIddoctor(),c.getFecha(),c.getHorainicio());
                citaRepository.delCita(c.getIdcita());
                if (!citaRepository.findById(c.getIdcita()).isPresent()) {
                    System.out.println("elimnada_:" + c.getIdcita());
                } else {
                    //se notifica por correo y notificaciones
                    //A paciente
                    notificacionesRepository.notificarCreacion(c.getPaciente().getUsuario().getIdusuario(), "Se cancelo su cita programado para el dia: " + c.getFecha() + " a las :" + c.getHorainicio() + " Por falta de pago",
                            "Cita Cancelada por Falta de Pago");
                    // A doctor
                    notificacionesRepository.notificarCreacion(c.getDoctor().getUsuario().getIdusuario(), "Estimado Doctor(a): Se cancelo su cita programado para el dia: " + c.getFecha() + " a las :" + c.getHorainicio() + " con el paciente: " +
                                    c.getPaciente().getUsuario().getNombres() + " " + c.getPaciente().getUsuario().getApellidos() + " por falta de pago del paciente",
                            "Cita Cancelada");

                    //correos


                    emailService.sendEmail(c.getPaciente().getUsuario().getCorreo(),
                            "Cita Cancelada por Falta de Pago",
                            "Se cancelo su cita programado para el dia: " + c.getFecha() + " de:" + c.getHorainicio() + "a " + c.getHorafinal() + "Por falta de pago");
                    emailService.sendEmail(c.getDoctor().getUsuario().getCorreo(),
                       "Cita Cancelada",
                          "Estimado Doctor(a): Se cancelo su cita programado para el dia: " + c.getFecha() + " de:" + c.getHorainicio() + "a " + c.getHorafinal() + " con el paciente: " +
                                 c.getPaciente().getUsuario().getNombres() + " " + c.getPaciente().getUsuario().getApellidos() + " por falta de pago del paciente");
                }
            } else if (LocalTime.now().minusHours(5).getHour()==c.getHorainicio().minusHours(2).getHour() && LocalTime.now().minusHours(5).getMinute()==c.getHorainicio().getMinute()) {
                System.out.println("entro aca");//avisar 2 horas antes
                emailService.sendEmail(c.getPaciente().getUsuario().getCorreo(),
                        "Recordatorio Cita: " + c.getFecha(),
                        "Estimado paciente: " + c.getPaciente().getUsuario().getNombres() + " " + c.getPaciente().getUsuario().getApellidos() + ", le  hacemos recordar que tiene una cita hoy en la sede: " +
                                c.getSede().getNombre() + " con el doctor " + c.getDoctor().getUsuario().getNombres() + " " + c.getDoctor().getUsuario().getNombres() +
                                "\nRecuerde llegar temprano hora: " + c.getHorainicio());
            }

        }


        //Actualizar en espera ,las citas pagaddas.
        /*for (Cita c : citaRepository.citasPagadasToday()) {
            if (LocalTime.now().isAfter(c.getHorainicio())) {
                citaRepository.actualizarEstadoCita(3,c.getIdcita());

            }
        }*/
    }

    @Async
    @Scheduled(fixedDelay = 1000*55) // Ejecutar cada segundo 
    @Transactional
    public void estadoPausa(){
        for (Cita c : citaRepository.citasToChangeStatus()){
            System.out.println(LocalTime.now().minusHours(5).isAfter(c.getHorainicio()) && LocalTime.now().minusHours(5).isBefore(c.getHorafinal()));
            if(LocalTime.now().minusHours(5).isAfter(c.getHorainicio()) && LocalTime.now().minusHours(5).isBefore(c.getHorafinal())){
                System.out.println("cita: " + c.getIdcita());
                citaRepository.actualizarEstadoCita(3,c.getIdcita());
            }
        }
    }
}
