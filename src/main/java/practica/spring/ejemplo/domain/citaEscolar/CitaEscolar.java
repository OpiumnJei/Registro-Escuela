package practica.spring.ejemplo.domain.citaEscolar;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practica.spring.ejemplo.domain.alumno.Alumno;
import practica.spring.ejemplo.domain.profesor.Profesor;

import java.time.LocalDateTime;
import java.util.Optional;

@Table(name = "citas")
@Entity(name = "Cita")
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CitaEscolar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;

    @Column(name="tutorAlumno")
    private String tutorAlumno;
    private LocalDateTime fecha;


    public CitaEscolar(Long id, Alumno alumno, Profesor profesor, String tutorAlumno, LocalDateTime fecha) {
        this.id = id;
        this.alumno = alumno;
        this.profesor = profesor;
        this.tutorAlumno = tutorAlumno;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getTutorAlumno() {
        return tutorAlumno;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}

