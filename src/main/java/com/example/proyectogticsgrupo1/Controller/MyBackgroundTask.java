package com.example.proyectogticsgrupo1.Controller;
import com.example.proyectogticsgrupo1.Entity.Cita;
import com.example.proyectogticsgrupo1.Entity.EmailService;
import com.example.proyectogticsgrupo1.Repository.CitaRepository;
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

    @Async
    @Scheduled(fixedDelay = 5000) // Ejecutar cada segundo
    @Transactional
    public void doBackgroundTask(){
        for(Cita c : citaRepository.citasProxToday()){
            System.out.println(c.getHorainicio());
            System.out.println(c.getHorainicio());
            System.out.println(LocalTime.now());
            if(c.getHorainicio().isBefore(LocalTime.now())){
                System.out.println(c.getIdcita());
                citaRepository.delCita(c.getIdcita());
                if(!citaRepository.findById(c.getIdcita()).isPresent()){
                    System.out.println("elimnada_:" + c.getIdcita());
                }else{
                    emailService.sendEmail(c.getPaciente().getUsuario().getCorreo(),
                            "Cita cancelada Automaticamente por falta de pago",
                            "Se canclo por no pagar pipi");
                    emailService.sendEmail(c.getDoctor().getUsuario().getCorreo(),
                            "Cita cancelada Automaticamente por falta de pago",
                            "Se canclo por no pagar pipi");
                }
            }else if (c.getHorainicio().equals(c.getHorainicio().minusHours(2))){
                emailService.sendEmail(c.getPaciente().getUsuario().getCorreo(),
                        "Recordatorio cita",
                        "EStiamdo cliente, le hacemos recordar que tiene una cita hoy en"+
                        c.getSede()+" con el doctor "+c.getDoctor().getUsuario().getNombres() + " " + c.getDoctor().getUsuario().getNombres() +
                        "\nRecuerde llegar temprano");
            }
        }
    }
}
