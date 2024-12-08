package edu.utp.pe.Service;

import edu.utp.pe.Dto.CitaDto;
import edu.utp.pe.Entity.*;
import edu.utp.pe.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


public interface CitaService {
    void guardarCita(Cita cita);
    List<Cita> listarCitas();
    List<CitaDto> listarCitasDto(String dni);
    Paciente getPacienteByDni(String dni);
    boolean actualizarCita(CitaDto citaDto);
}
