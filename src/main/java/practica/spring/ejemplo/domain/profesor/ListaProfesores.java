package practica.spring.ejemplo.domain.profesor;

public record ListaProfesores(Long id, String nombre, String asignatura_impartida) {

    public ListaProfesores(Profesor profesor){
        this(profesor.getId(),profesor.getNombre(), profesor.getAsignatura());
    }
}
