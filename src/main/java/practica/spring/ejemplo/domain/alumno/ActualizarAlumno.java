package practica.spring.ejemplo.domain.alumno;

import jakarta.validation.constraints.NotNull;

public record ActualizarAlumno(@NotNull Long id, String nombre, String matricula) {
}
