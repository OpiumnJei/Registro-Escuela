package practica.spring.ejemplo.domain.citaEscolar;

import java.time.LocalDateTime;

//datos a mostrar al cliente una vez se guarde el objeto en formato json(se cree correctamente una cita escolar)
public record RespuestaCitaEscolar(
        Long id,
        Long idAlumno,
        Long idProfesor,
        String tutorAlumno,
        LocalDateTime fecha
) {
}
