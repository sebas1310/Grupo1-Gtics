package com.example.proyectogticsgrupo1.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class spa_controller_2 {


    @PostMapping("/spa_controller_2_recibir")
    public ResponseEntity<String> recibirDatosJson(@RequestBody Datos datos) {

        System.out.println("entra al controller");
        // Aquí puedes realizar las operaciones necesarias con los datos recibidos
        // Por ejemplo, puedes acceder a los atributos de la clase Datos y procesarlos

        // Devolver una respuesta al cliente
        String mensaje = "Los datos fueron recibidos correctamente";
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    // Clase utilizada para mapear la estructura de los datos JSON
    public static class Datos {
        private String nombre;
        private int edad;
        private String correo;


        private String datos;
        private String nombreplantilla;
        private int id_rol;

        private int id_especialidad;

        private int nro_inputs;

        private String tipo_plantilla;

        // Getters y setters


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getDatos() {
            return datos;
        }

        public void setDatos(String datos) {
            this.datos = datos;
        }

        public String getNombreplantilla() {
            return nombreplantilla;
        }

        public void setNombreplantilla(String nombreplantilla) {
            this.nombreplantilla = nombreplantilla;
        }

        public int getId_rol() {
            return id_rol;
        }

        public void setId_rol(int id_rol) {
            this.id_rol = id_rol;
        }

        public int getId_especialidad() {
            return id_especialidad;
        }

        public void setId_especialidad(int id_especialidad) {
            this.id_especialidad = id_especialidad;
        }

        public int getNro_inputs() {
            return nro_inputs;
        }

        public void setNro_inputs(int nro_inputs) {
            this.nro_inputs = nro_inputs;
        }

        public String getTipo_plantilla() {
            return tipo_plantilla;
        }

        public void setTipo_plantilla(String tipo_plantilla) {
            this.tipo_plantilla = tipo_plantilla;
        }

        public Datos(String nombre, int edad, String correo, String datos, String nombreplantilla, int id_rol, int id_especialidad, int nro_inputs, String tipo_plantilla) {
            this.nombre = nombre;
            this.edad = edad;
            this.correo = correo;
            this.datos = datos;
            this.nombreplantilla = nombreplantilla;
            this.id_rol = id_rol;
            this.id_especialidad = id_especialidad;
            this.nro_inputs = nro_inputs;
            this.tipo_plantilla = tipo_plantilla;
        }

        // Constructor
        public Datos() {}
    }


}
