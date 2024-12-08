package edu.utp.pe.ServiceImpl;

import edu.utp.pe.Entity.Especialidad;
import edu.utp.pe.Entity.Paciente;
import edu.utp.pe.Repository.EspecialidadRepository;
import edu.utp.pe.Service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.List;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public List<Especialidad> listarEspecialidades() {
        return especialidadRepository.findAll();
    }
}
