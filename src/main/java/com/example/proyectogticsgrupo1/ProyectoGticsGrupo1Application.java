package com.example.proyectogticsgrupo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootApplication
public class ProyectoGticsGrupo1Application {

    public static void main(String[] args) {

        SpringApplication.run(ProyectoGticsGrupo1Application.class, args);
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public String error() {
            return "error"; // Nombre de la vista de error, por ejemplo, "error.html"
        }
    }

}
