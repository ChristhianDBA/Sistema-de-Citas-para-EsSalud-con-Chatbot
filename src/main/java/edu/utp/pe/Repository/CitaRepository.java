package edu.utp.pe.Repository;

import edu.utp.pe.Dto.CitaDto;
import edu.utp.pe.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query("SELECT new edu.utp.pe.Dto.CitaDto(e.nombre, CONCAT(d.nombre, ' ', d.apellido), c.fecha, c.hora, CONCAT(u.pabellon, u.piso, u.numeroSalon), c.estado) " +
            "FROM Cita c " +
            "JOIN c.paciente p " +
            "JOIN c.doctor d " +
            "JOIN c.especialidad e " +
            "JOIN c.ubicacion u " +
            "WHERE p.dni = :dni " + "ORDER BY c.fecha DESC")
    List<CitaDto> findCitasByPacienteDni(@Param("dni") String dni);

    @Modifying
    @Transactional
    @Query("UPDATE Cita c SET c.fecha = :fecha, c.hora = :hora, c.ubicacion.id = :ubicacion, c.estado = :estado " +
            "WHERE c.especialidad.nombre = :especialidad AND CONCAT(c.doctor.nombre, ' ', c.doctor.apellido) = :especialista")
    int updateCita(@Param("especialidad") String especialidad,
                   @Param("especialista") String especialista,
                   @Param("fecha") LocalDate fecha,
                   @Param("hora") LocalTime hora,
                   @Param("ubicacion") Long ubicacion,         // Cambiar a Long
                   @Param("estado") String estado);


}
