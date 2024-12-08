package edu.utp.pe.Service;

import edu.utp.pe.Entity.Cita;
import edu.utp.pe.Entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteService {
    Paciente buscarPorDni(String dni);
    Paciente guardarPaciente(Paciente paciente);
}
