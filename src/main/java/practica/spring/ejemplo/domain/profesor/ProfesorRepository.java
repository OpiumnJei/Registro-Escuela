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
            WHERE p.activo = 1 
            AND p.asignaturaImpartida = :asignaturaImpartida
            AND p.id NOT IN (
                SELECT c.profesor.id FROM CitaEscolar c
                WHERE c.fecha = :fecha
            )
            ORDER BY RAND()
            """)
    Profesor SeleccionarProfesorConAsignaturaEnFecha(String asignaturaImpartida, LocalDateTime fecha);
}
