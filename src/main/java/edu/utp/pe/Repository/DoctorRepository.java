package edu.utp.pe.Repository;

import edu.utp.pe.Entity.Doctor;
import edu.utp.pe.Entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
