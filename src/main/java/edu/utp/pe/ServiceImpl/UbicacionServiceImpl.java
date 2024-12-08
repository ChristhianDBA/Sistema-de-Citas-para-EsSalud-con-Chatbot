package edu.utp.pe.ServiceImpl;

import edu.utp.pe.Entity.Ubicacion;
import edu.utp.pe.Repository.UbicacionRepository;
import edu.utp.pe.Service.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbicacionServiceImpl implements UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;


    @Override
    public List<Ubicacion> listarUbicaciones() {
        return ubicacionRepository.findAll();
    }
}