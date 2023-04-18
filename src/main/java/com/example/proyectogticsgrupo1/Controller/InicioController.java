package com.example.proyectogticsgrupo1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/inicioSesion")

public class InicioController {
    @GetMapping(value = "/")
    public String inicioSesion(){
        return "pages-login.html";
    }

    @GetMapping(value = "/olvidoContra")
    public String olvidoContra(){
        return "olvidocontra.html";
    }

    @GetMapping(value = "/formRegistro")
    public String formRegistro(){
        return "olvidocontr.html";
    }



}





