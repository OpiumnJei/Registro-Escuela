package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolar;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolarRepository;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCancelaciones;
import practica.spring.ejemplo.infra.errores.ValidacionDeDatos;

import java.time.Duration;
import java.time.LocalDateTime;

//UNA CITA SOLO PUEDE SER CANCELADA CON 24H DE ANTICIPACION
@Component
public class CancelarCitaAntesDia implements IValidarCancelaciones{
    @Autowired
    private CitaEscolarRepository citaEscolarRepository;

    @Override
    public void validarCancelacion(GuardarDatosCancelaciones datosCancelaciones) {
        //buscar en la base de datos el id de la cita en
        CitaEscolar cita = citaEscolarRepository.findById(datosCancelaciones.idCita())
                .orElseThrow(() -> new ValidacionDeDatos("Cita no encontrada"));

        //hora en la que se crea la cita
        var ahora = LocalDateTime.now();
        //verificar que entre la fecha de la cita y la fecha actual haya una diferencia de 1 dia - 24h
        var fechaValida = cita.getFecha().minusHours(24).isBefore(ahora);

        //debe haber almenos 24 horas entre la fecha de la cita creada, y la fecha de la cancelacion(ahora)
        if (fechaValida){
            throw new ValidationException("Las citas solo se pueden cancelar con al menos 24 horas de anticipación");
        }

        //otra forma aun mas sencilla
        // var fechaValida = Duration.between(ahora, cita.getFecha()).toHours()<24;
        // if(fechaValida)
    }
}
