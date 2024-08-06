package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;
import practica.spring.ejemplo.domain.profesor.ProfesorRepository;

//VALIDAR QUE UN PROFESOR AUN ESTE IMPARTIENDO DOCENCIA(TRABAJANDO)
@Component
public class ProfesorActivo implements IValidarCitasEscolares{

    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public void validar(GuardarDatosCitaEscolar datosCitaEscolar){

        //comprobar que el id introducido por el cliente no se nulo
        if(datosCitaEscolar.idProfesor() == null){
            return;
        }
        //buscar en la base de datos el id introducido, la variable retorna un valor boleano
        var profesorActivo = profesorRepository.findActivoById(datosCitaEscolar.idProfesor());

        //si no se encuentra el id
        if(!profesorActivo){
            throw new ValidationException("No se pueden programar citas con profesores inactivos.");
        }

    }
}
