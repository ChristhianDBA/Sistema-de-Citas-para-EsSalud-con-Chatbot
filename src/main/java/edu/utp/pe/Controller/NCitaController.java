package edu.utp.pe.Controller;

import edu.utp.pe.Dto.CitaDto;
import edu.utp.pe.Entity.Paciente;
import edu.utp.pe.Service.CitaService;
import edu.utp.pe.Service.UbicacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.text.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class NCitaController {
    @Autowired
    private CitaService citaService;
    @Autowired
    private UbicacionService ubicacionService;

    private static final Logger logger = LoggerFactory.getLogger(NCitaController.class);

    // Crear una lista para almacenar las citas
    List<CitaDto> citasList = new ArrayList<>();


    @GetMapping("/paciente/{dni}")
    public ResponseEntity<?> getPacienteYCitas(@PathVariable String dni) {
        Paciente paciente = citaService.getPacienteByDni(dni);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ningún paciente con el DNI proporcionado.");
        }

        List<CitaDto> citas = citaService.listarCitasDto(dni);

        // Construir respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("nombreCompleto", paciente.getNombreCompleto());
        response.put("citas", citas);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/editarCita")
    public String editarCita(
            @RequestParam String especialidad,
            @RequestParam String especialista,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam String ubicacion,
            @RequestParam String estado,
            Model model) {

        logger.info("Recibida solicitud para editar cita: especialidad={}, especialista={}, fecha={}, hora={}, ubicacion={}",
                especialidad, especialista, fecha, hora, ubicacion);


        CitaDto cita = new CitaDto();
        cita.setEspecialidad(especialidad);
        cita.setEspecialista(especialista);
        cita.setFecha(LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE));
        cita.setHora(LocalTime.parse(hora, DateTimeFormatter.ISO_TIME));
        cita.setUbicacion(ubicacion);
        cita.setEstado(estado);

        // Agregar la cita creada a la lista
        citasList.add(cita);

        model.addAttribute("notificacion", citasList);
        model.addAttribute("cita", cita);
        model.addAttribute("ubicaciones", ubicacionService.listarUbicaciones());

        logger.info("Retornando vista 'editar'");
        return "editar";
    }

    @PostMapping("/actualizarCita")
    public String actualizarCita(@ModelAttribute CitaDto citaDto, Model model) {
        logger.debug("Recibida solicitud para actualizar cita: {}", citaDto);
        boolean actualizado = citaService.actualizarCita(citaDto);
        if (actualizado) {
            logger.info("Cita actualizada correctamente");
            model.addAttribute("mensaje", "Cita actualizada correctamente.");
        } else {
            logger.warn("Error al actualizar la cita");
            model.addAttribute("error", "Error al actualizar la cita.");
        }
        return "resultado";
    }
}