package com.example.proyectogticsgrupo1.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class ErrorController {

    @RequestMapping
    public String handleError() {
        // Lógica para manejar el error y retornar el nombre de la vista del error
        return "error"; // Nombre de la vista de error, por ejemplo, "error.html"
    }
}