package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;

import java.time.Duration;
import java.time.LocalDateTime;

//CADA CITA ESCOLAR DEBE HACERSE CON UNA 1H DE ANTICIPACION
@Component
public class HoraAnticipacionCita implements IValidarCitasEscolares{

    public void validar(GuardarDatosCitaEscolar datosCitaEscolar){

        //ahora
        var horaActual = LocalDateTime.now();
        //hora en la que se hara la cita escolar
        var horaCitaEscolar = datosCitaEscolar.fecha(); 

        //debe de haber una diferencia entre la horaActual y la horaConsulta de 1h
        var tiempoValidoParaCita = Duration.between(horaActual, horaCitaEscolar).toHours()<1;

        //si la diferencia es menor a una hora
        if(tiempoValidoParaCita){
            throw new ValidationException("Las citas deben hacerse con almenos una(1) hora de anticipacion");
        }

    }
}
