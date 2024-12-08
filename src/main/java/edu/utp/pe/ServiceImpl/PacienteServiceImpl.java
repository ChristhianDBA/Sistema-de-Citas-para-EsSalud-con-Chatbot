package edu.utp.pe.ServiceImpl;

import edu.utp.pe.Entity.Cita;
import edu.utp.pe.Entity.Paciente;
import edu.utp.pe.Repository.CitaRepository;
import edu.utp.pe.Repository.PacienteRepository;
import edu.utp.pe.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Paciente buscarPorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }
}
