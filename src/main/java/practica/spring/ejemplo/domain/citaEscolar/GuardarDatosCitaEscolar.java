package practica.spring.ejemplo.domain.citaEscolar;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

//datos que seran enviados desde el cliente/postman
public record GuardarDatosCitaEscolar(

        Long id,

        @NotNull
        Long idAlumno,

        Long idProfesor,

        @NotBlank
        String tutorAlumno,

        @NotNull
        @Future
        LocalDateTime fecha,

        String asignaturaImpartida
) {
}
