package com.example.proyectogticsgrupo1;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@SpringBootApplication
public class ProyectoGticsGrupo1Application {

    public static void main(String[] args) {

        SpringApplication.run(ProyectoGticsGrupo1Application.class, args);
    }

    @Configuration
    public class ErrorConfig {

        @Bean
        public ErrorViewResolver customErrorViewResolver() {
            return (HttpServletRequest request, HttpStatus status, Map<String, Object> model) -> {
                ModelAndView modelAndView = new ModelAndView("error"); // Nombre de tu página de error personalizada
                modelAndView.addObject("status", status.value());
                modelAndView.addObject("error", status.getReasonPhrase());
                return modelAndView;
            };
        }
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/assets/**")
                    .addResourceLocations("classpath:/static/assets/");
        }
    }


}
