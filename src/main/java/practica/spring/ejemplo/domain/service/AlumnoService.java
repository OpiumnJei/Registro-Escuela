package practica.spring.ejemplo.domain.service;

import org.springframework.stereotype.Service;
import practica.spring.ejemplo.domain.alumno.Alumno;
import practica.spring.ejemplo.domain.alumno.AlumnoRepository;

@Service
public class AlumnoService {
    private AlumnoRepository repoAlumno;

    public void guardarAlumno(Alumno datos){
        repoAlumno.save(datos);
    }
}
