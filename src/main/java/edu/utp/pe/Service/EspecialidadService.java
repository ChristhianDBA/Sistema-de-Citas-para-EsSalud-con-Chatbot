package edu.utp.pe.Service;

import edu.utp.pe.Entity.Especialidad;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EspecialidadService {
    List<Especialidad> listarEspecialidades();
}
