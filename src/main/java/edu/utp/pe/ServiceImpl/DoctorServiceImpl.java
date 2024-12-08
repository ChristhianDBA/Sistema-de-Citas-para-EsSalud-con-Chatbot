package edu.utp.pe.ServiceImpl;

import edu.utp.pe.Entity.Doctor;
import edu.utp.pe.Repository.DoctorRepository;
import edu.utp.pe.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public List<Doctor> listarDoctores() {
        return doctorRepository.findAll();
    }
}
