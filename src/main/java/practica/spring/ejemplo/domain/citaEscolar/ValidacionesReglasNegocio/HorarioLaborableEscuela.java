package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;

import java.time.DayOfWeek;

//NO SE LABORAN LOS SABADOS NI LOS DOMINGOS
@Component
public class HorarioLaborableEscuela implements IValidarCitasEscolares{

    //metodo para validar los dias en que se podran crear citas escolares
    public void validar(GuardarDatosCitaEscolar datosCitaEscolar){

        // se compara el valor del enum SATURDAY con el valor enviado en fecha,
        //si la fecha coincide con el dia elegido en el enum retorna True de lo contrario False
        var sabado = DayOfWeek.SATURDAY.equals(datosCitaEscolar.fecha().getDayOfWeek());
        var domingo = DayOfWeek.SUNDAY.equals(datosCitaEscolar.fecha().getDayOfWeek());

        var horaAntesIniciarJornada = datosCitaEscolar.fecha().getHour()<7; //si la hora introducida es menor a las 7 retorna true
        var horaDespuesTerminarJornada = datosCitaEscolar.fecha().getHour()>16; //si la hora introducida es mayor a 16 retorna true
        if(sabado || domingo || horaAntesIniciarJornada || horaDespuesTerminarJornada){
            throw new ValidationException("Disculpe, solo laboramos de 7:00 AM a 4:00 PM de Lunes a Viernes");
        }
    }
}
