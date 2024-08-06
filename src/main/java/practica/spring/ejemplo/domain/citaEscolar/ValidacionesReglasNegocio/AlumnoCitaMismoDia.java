package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolarRepository;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;

@Component
public class AlumnoCitaMismoDia implements IValidarCitasEscolares {

    @Autowired
    private CitaEscolarRepository citaEscolarRepository;

    public void validar(GuardarDatosCitaEscolar datosCitaEscolar){

        //hora que de inicio
        var iniciaJornada = datosCitaEscolar.fecha().withHour(7);
        //hora concluye la jornada
        var terminaJornada = datosCitaEscolar.fecha().withHour(16);

        //comprueba si un alumno ya tiene una cita registrada el mismo dia, es decir,
        // en el mismo intervalo de tiempo especificado en las variables de arriba
        var alumnoEnCita = citaEscolarRepository.existsByAlumnoIdAndFechaBetween(datosCitaEscolar.idAlumno(),iniciaJornada, terminaJornada);

        //si un alumno tiene una cita en el mismo dia
        if(alumnoEnCita){
            throw new ValidationException("Un alumno no puede tener dos citas el mismo dia.");
        }
    }
}
