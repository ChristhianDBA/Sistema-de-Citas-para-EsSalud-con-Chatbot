package edu.utp.pe.Controller;

import edu.utp.pe.Entity.*;
import edu.utp.pe.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Controller
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UbicacionService ubicacionService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        List<Especialidad> especialidades = especialidadService.listarEspecialidades();
        List<Doctor> doctores = doctorService.listarDoctores();
        List<Ubicacion> ubicaciones = ubicacionService.listarUbicaciones();

        System.out.println("Especialidades: " + especialidades);
        System.out.println("Doctores: " + doctores);
        System.out.println("Ubicaciones: " + ubicaciones);

        model.addAttribute("cita", new Cita());
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("especialidades", especialidades);
        model.addAttribute("doctores", doctores);
        model.addAttribute("ubicaciones", ubicaciones);

        for (Doctor doctor : doctores) {
            System.out.println("Doctor ID: " + doctor.getId() + ", Nombre: " + doctor.getNombre() + ", Apellido: " + doctor.getApellido());
        }

        System.out.println("----------------");

        ubicaciones.forEach(ubicacion ->
                System.out.println("Ubicacion: " + ubicacion.getId() + ", Descripción: " + ubicacion.getPiso() + " " + ubicacion.getPabellon()+" "+ubicacion.getNumeroSalon()));


        return "crearcita";
    }

    @PostMapping("/form/guardar")
    public String guardarCita(Cita cita) {

        // Asegurarse de que el estado "Pendiente" se asigna correctamente
        if (cita.getEstado() == null || cita.getEstado().equals("")) {
            cita.setEstado("Pendiente");
        }

        // Verificar si el paciente ya existe
        Paciente pacienteExistente = pacienteService.buscarPorDni(cita.getPaciente().getDni());
        if (pacienteExistente == null) {
            // Guardar nuevo paciente si no existe
            pacienteExistente = pacienteService.guardarPaciente(cita.getPaciente());
        }
        // Asociar el paciente existente o recién creado a la cita
        cita.setPaciente(pacienteExistente);

        // Guardar la cita
        citaService.guardarCita(cita);

        // Redirigir de nuevo al formulario para crear más citas
        return "redirect:/form";  // NOTA: Asegúrate de que la ruta sea correcta
    }

}
