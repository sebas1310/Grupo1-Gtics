package com.example.proyectogticsgrupo1.Controller;
import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.EmailService;
import com.example.proyectogticsgrupo1.Repository.BoletaDoctorRepository;
import com.example.proyectogticsgrupo1.Repository.BoletaPacienteRepository;
import com.example.proyectogticsgrupo1.Repository.CitaRepository;
import com.example.proyectogticsgrupo1.Repository.NotificacionesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Async
    @Scheduled(fixedDelay = 5000) // Ejecutar cada segundo
    @Transactional
    public void doBackgroundTask() {
        //iteramos las citas de hoy dia , que son estado 1: (proxima cita) y virtuales
        for (Cita c : citaRepository.citasVirtualesToday()) {
            //System.out.println(c.getHorainicio());
            //System.out.println(c.getHorainicio());
            System.out.println(LocalTime.now());
            //si la hora actual es igual a la hora de inicio de la cita menos 1 , entonces se canelara por falta de pago
            if (LocalTime.now().isAfter(c.getHorainicio().minusHours(1))) {
                //System.out.println(LocalTime.now());
                System.out.println(c.getIdcita());
                //se elimina boletas primero
                boletaPacienteRepository.delBoletaPaciente(c.getIdcita());
                boletaDoctorRepository.delBoletaDoctor(c.getIdcita());
                //y de ahi la cita
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
            } else if (c.getHorainicio().equals(c.getHorainicio().minusHours(2))) {
                emailService.sendEmail(c.getPaciente().getUsuario().getCorreo(),
                        "Recordatorio Cita: " + c.getFecha(),
                        "Estimado paciente: " + c.getPaciente().getUsuario().getNombres() + " " + c.getPaciente().getUsuario().getApellidos() + ", le hacemos recordar que tiene una cita hoy en la sede: " +
                                c.getSede().getNombre() + " con el doctor " + c.getDoctor().getUsuario().getNombres() + " " + c.getDoctor().getUsuario().getNombres() +
                                "\nRecuerde llegar temprano");
            }
        }

        for (Cita c : citaRepository.citasPresencialesToday()) {
            if (c.getHorainicio().equals(c.getHorainicio().minusHours(2))) {
                emailService.sendEmail(c.getPaciente().getUsuario().getCorreo(),
                        "Recordatorio Cita: " + c.getFecha(),
                        "Estimado paciente: " + c.getPaciente().getUsuario().getNombres() + " " + c.getPaciente().getUsuario().getApellidos() + ", le hacemos recordar que tiene una cita hoy en la sede: " +
                                c.getSede().getNombre() + " con el doctor " + c.getDoctor().getUsuario().getNombres() + " " + c.getDoctor().getUsuario().getNombres() +
                                "\nRecuerde llegar temprano y cancelar en caja");
            }
        }
    }
}
