package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practica.spring.ejemplo.domain.alumno.AlumnoRepository;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;

//VALIDAR QUE UN ESTUDIANTE AUN SE ESCUENTRE ACTIVO(ESTUDIANDO)
@Component
public class AlumnoActivo implements IValidarCitasEscolares{

    @Autowired
    private AlumnoRepository alumnoRepository;

    public void validar(GuardarDatosCitaEscolar datosCitaEscolar){

        //comprobar que el id introducido por el cliente no se nulo
        if(datosCitaEscolar.idAlumno() == null){
            return;
        }

        //buscar en la base de datos el id introducido, la variable retorna un valor boleano
        var AlumnoActivo = alumnoRepository.findByIdActivo(datosCitaEscolar.idAlumno());

        //si el retorno es false, es decir, que esta inactivo
        if(!AlumnoActivo){
            throw new ValidationException("No se pueden programar citas con estudiantes inactivos.");
        }
    }
}
