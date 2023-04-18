package com.example.proyectogticsgrupo1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/administrador")

public class AdministradorController {
        @GetMapping(value = "/")
        public String paciente() {
            return "administrador/dashboard";
        }


    }


