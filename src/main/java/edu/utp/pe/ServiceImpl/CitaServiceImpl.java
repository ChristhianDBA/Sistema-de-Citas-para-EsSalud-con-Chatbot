package edu.utp.pe.ServiceImpl;

import edu.utp.pe.Dto.CitaDto;
import edu.utp.pe.Entity.Cita;
import edu.utp.pe.Entity.Paciente;
import edu.utp.pe.Repository.CitaRepository;
import edu.utp.pe.Repository.PacienteRepository;
import edu.utp.pe.Service.CitaService;
import edu.utp.pe.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;


    @Override
    public void guardarCita(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @Override
    public List<CitaDto> listarCitasDto(String dni) {
        return citaRepository.findCitasByPacienteDni(dni);
    }

    @Override
    public Paciente getPacienteByDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    @Override
    public boolean actualizarCita(CitaDto citaDto) {
        int updated = citaRepository.updateCita(
                citaDto.getEspecialidad(),
                citaDto.getEspecialista(),
                citaDto.getFecha(),
                citaDto.getHora(),
                Long.valueOf(citaDto.getUbicacion()),
                citaDto.getEstado()
        );
        return updated > 0;
    }


}
