package com.example.proyectogticsgrupo1.Controller;


import com.example.proyectogticsgrupo1.Repository.PacienteRepository;
import com.example.proyectogticsgrupo1.Repository.DoctorRepository;
import com.example.proyectogticsgrupo1.Repository.FormularioadministrativoRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

final PacienteRepository pacienteRepository;
final DoctorRepository doctorRepository;
final FormularioadministrativoRepository formularioadministrativoRepository;
public class PacienteController {


    public PacienteController(PacienteRepository employeesRepository, DoctorRepository departmentsRepository, FormularioadministrativoRepository jobsRepository ) {
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
        this.formularioadministrativoRepository = formularioadministrativoRepository;
    }

    @GetMapping("/administrativo")
    public String inicioPacientes(Model model){
        List<Paciente> listaPacientes = PacienteRepository.listarPacientes();
        model.addAttribute("listaPacientes",listaPacientes);
        return "administrativo/dashboard";
    }


    @PostMapping("/empleado/buscar")
    public String buscarPacientes(@RequestParam("textoaBuscar") String searchText, Model model){
        List<Employee> listaEmpleados = employeeRepository.buscarEmpleados(searchText);
        model.addAttribute("listaEmpleados",listaEmpleados);
        model.addAttribute("textoaBuscar",searchText);
        return "administrativo/dashboard";
    }

    @GetMapping("/empleado/nuevo")
    public String empleadosNuevo(Model model){
        List<Job> listaPuestos = jobRepository.findAll();
        List<Department> listaDepartamentos = departmentRepository.findAll();
        List<Employee> listaJefes = employeeRepository.listarEmpleados();
        model.addAttribute("listaPuestos",listaPuestos);
        model.addAttribute("listaJefes",listaJefes);
        model.addAttribute("listaDepartamentos",listaDepartamentos);
        return "empleado/nuevoEmpleado";
    }


    @PostMapping("/paciente/guardar")
    public String guardarPaciente(
            @RequestParam("direccion") String direccion,
            @RequestParam("idestadopaciente") int idestadopaciente,
            @RequestParam("idseguro") int idseguro,
            @RequestParam("alergias") String alergias,
            @RequestParam("consentimientos") int consentimientos ,
            @RequestParam("idusuario") int idusuario,
            @RequestParam("condicion_enfermedad") String condicion_enfermedad,
            @RequestParam("poliza") String poliza,
            @RequestParam("referido") Boolean referido ,
            RedirectAttributes redirectAttributes
    ){

        PacienteRepository.guardarPaciente( direccion, idestadopaciente, idseguro, alergias, consentimientos, idusuario, condicion_enfermedad, poliza, referido);
        redirectAttributes.addFlashAttribute("msgGreen","Empleado creado exitosamente");
        return "redirect:/administrativo/";
    }


    @GetMapping("/empleado/editar")
    public String empleadosEditar(Model model,  @RequestParam("id") int employee_id, RedirectAttributes redirectAttributes){


        Optional<Employee> optEmployee = employeeRepository.findById(employee_id);


        if (optEmployee.isPresent()) {
            Employee empleado = optEmployee.get();
            List<Job> listaPuestos = jobRepository.findAll();
            List<Department> listaDepartamentos = departmentRepository.findAll();
            List<Employee> listaJefes = employeeRepository.listarEmpleados();
            model.addAttribute("listaPuestos",listaPuestos);
            model.addAttribute("listaJefes",listaJefes);
            model.addAttribute("listaDepartamentos",listaDepartamentos);
            model.addAttribute("empleado", empleado);
            return "empleado/editarEmpleado";
        } else {
            redirectAttributes.addFlashAttribute("msgRed","No se encontró al empleado");
            return "redirect:/empleado";
        }


    }

    @PostMapping("/administrativo/guardareditar")
    public String editarEmpleado(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("email") String email,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("puesto") String puesto_id,
            @RequestParam("sueldo") double sueldo,
            @RequestParam("EmpleadoJefe") int jefe_id,
            @RequestParam("departamento") int departamento_id,
            @RequestParam("id") int employee_id,
            RedirectAttributes redirectAttributes
    ){

        employeeRepository.editarEmpleado( nombre,  apellido,  email,  contrasena,  puesto_id,  sueldo,  jefe_id,  departamento_id, employee_id);
        redirectAttributes.addFlashAttribute("msgGreen","Empleado editado exitosamente");
        return "redirect:/empleado";
    }


    @GetMapping("empleado/borrar")
    public String borrarEmpleado(
            @RequestParam("id") int employee_id,
            RedirectAttributes redirectAttributes
    ){

        Optional<Employee> optEmployee = employeeRepository.findById(employee_id);


        if (optEmployee.isPresent()) {
            employeeRepository.borrarEmpleado(employee_id);
            redirectAttributes.addFlashAttribute("msgGreen","Empleado borrado exitosamente");
            return "redirect:/empleado";
        } else {
            redirectAttributes.addFlashAttribute("msgRed","No se encontró al empleado");
            return "redirect:/empleado";
        }

    }



}
