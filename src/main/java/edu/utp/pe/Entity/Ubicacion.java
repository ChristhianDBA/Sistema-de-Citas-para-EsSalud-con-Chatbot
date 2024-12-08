package edu.utp.pe.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "cita")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String piso;
    private String pabellon;
    private String numeroSalon;

    public String getNombreCompleto() {
        return pabellon + " " + piso + " " + numeroSalon;
    }

    @OneToMany(mappedBy = "ubicacion", cascade = CascadeType.ALL)
    private List<Cita> cita;

}
