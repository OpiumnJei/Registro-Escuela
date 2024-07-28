package practica.spring.ejemplo.domain.alumno;

public record RespuestaAlumno (
        Long id,
        String nombre,

        String apellido,


        String matricula,

        String correo,

        String telefono,

        String grado

){
}
