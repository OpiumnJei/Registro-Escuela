package practica.spring.ejemplo.domain.alumno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Page: Una interfaz de Spring Data JPA que representa una página de datos
//Pageable: Una interfaz que encapsula los parámetros de paginación (número de página, tamaño de la página, ordenación)
public interface AlumnoRepository extends JpaRepository<Alumno, Long>{

    Page<Alumno> findByActivoTrue(Pageable paginacion);

    //selecciona el campo activo de la entidad Alumno
    // y luego compara que el id del alumno sea el mismo introducido por el usuario
    @Query("""
            SELECT a.activo
            FROM Alumno a
            WHERE a.id=:idAlumno
            """)
    Boolean findByIdActivo(Long idAlumno);
}
