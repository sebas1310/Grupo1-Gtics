package com.example.proyectogticsgrupo1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")

public class InicioController {
    @GetMapping(value = {"/","/iniciosesion"})
    public String inicioSesion(){

        return "pages-login";
    }

    @GetMapping(value = "/registro")
    public String registro(){

        return "pages-register";
    }

    @GetMapping(value = "/olvidocontra")
    public String olvidoContra(){

        return "olvidocontra";
    }

    @GetMapping(value = "/formRegistro")
    public String formRegistro(){

        return "formularioregistro";
    }





}





