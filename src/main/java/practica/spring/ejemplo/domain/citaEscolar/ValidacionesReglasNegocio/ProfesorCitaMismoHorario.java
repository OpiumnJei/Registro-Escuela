package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolarRepository;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;

//VALIDAR QUE UN PROFESOR NO TENGA CITAS EN EL MISMO HORARIO
@Component
public class ProfesorCitaMismoHorario implements IValidarCitasEscolares{

    @Autowired
    private CitaEscolarRepository citaEscolarRepository;

    @Override
    public void validar(GuardarDatosCitaEscolar datosCitaEscolar){

        //Busca almenos un registro en la base de datos que coincida con los
        // criterios de busqueda especificados en la derived query
        boolean ProfesorConCita = citaEscolarRepository.existsByProfesorIdAndFecha(datosCitaEscolar.idProfesor(), datosCitaEscolar.fecha());

        //si encuentra la misma fecha
        if(ProfesorConCita){
            throw new ValidationException("El profesor ya tiene una cita programada en el mismo horario, seleccione otro por favor");
        }

    }

}
