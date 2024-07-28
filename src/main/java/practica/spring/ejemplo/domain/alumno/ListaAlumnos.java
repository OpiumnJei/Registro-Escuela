package practica.spring.ejemplo.domain.alumno;

import jakarta.validation.constraints.NotNull;
import practica.spring.ejemplo.domain.alumno.Alumno;

public record ListaAlumnos(@NotNull Long id, String nombre, String apellido, String matricula)
{
    public ListaAlumnos(Alumno alumno){
        this(alumno.getId(),alumno.getNombre(), alumno.getApellido(), alumno.getMatricula());
    }
}
