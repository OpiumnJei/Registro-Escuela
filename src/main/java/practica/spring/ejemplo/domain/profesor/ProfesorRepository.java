package practica.spring.ejemplo.domain.profesor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

//Page: Una interfaz de Spring Data JPA que representa una página de datos
//Pageable: Una interfaz que encapsula los parámetros de paginación (número de página, tamaño de la página, ordenación)
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    //buscar profesores en la base de datos que aun esten activos(trabajando)
    Page<Profesor> findByActivoTrue(Pageable pageable);

    //crear consulta JPQL
    @Query("""
            SELECT p FROM Profesor p
            WHERE p.activo = true
            AND p.asignatura_impartida = :asignaturaImpartida
            AND p.id NOT IN (
                SELECT c.profesor.id FROM Cita c
                WHERE c.fecha = :fecha
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Profesor SeleccionarProfesorConAsignaturaEnFecha(String asignaturaImpartida, LocalDateTime fecha);

    //selecciona el campo activo de la entidad Profesor
    //y luego compara que el id del Profesor sea el mismo introducido por el usuario
    @Query("""
            SELECT p.activo
            FROM Profesor p
            WHERE p.id=:idProfesor
            """)
    Boolean findActivoById(Long idProfesor);
}
