package practica.spring.ejemplo.domain.profesor;

public record ResponseProfesor(
        Long id,
        String nombre,
        String asignatura_impartida,

        String correo,

        String telefono
) {
}
