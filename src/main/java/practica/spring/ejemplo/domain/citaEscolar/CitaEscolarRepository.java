package practica.spring.ejemplo.domain.citaEscolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CitaEscolarRepository extends JpaRepository<CitaEscolar, Long> {

    //metodo que verifica si existe un alumno con una cita registrada en el mismo dia
    //verifica que exista almenos un registro en la bd usando el id de un
    //alumno y verifica que si el registro esta entre las horas laborables, de ser asi retorna un true
    Boolean existsByAlumnoIdAndFechaBetween(Long alumnoId, LocalDateTime iniciaJornada, LocalDateTime terminaJornada);

    //verifica que exista almenos un registro en la base de datos que conincida con los parametros espeficiados en el metodo
    Boolean existsByProfesorIdAndFecha(Long profesorId, LocalDateTime fecha);
}
