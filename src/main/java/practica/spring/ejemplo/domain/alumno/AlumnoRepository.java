package practica.spring.ejemplo.domain.alumno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//Page: Una interfaz de Spring Data JPA que representa una página de datos
//Pageable: Una interfaz que encapsula los parámetros de paginación (número de página, tamaño de la página, ordenación)
public interface AlumnoRepository extends JpaRepository<Alumno, Long>{

    Page<Alumno> findByActivoTrue(Pageable paginacion);
}
