package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCancelaciones;
import practica.spring.ejemplo.infra.errores.ValidacionDeDatos;

//UNA CANCELACION DEBE TENER UNA JUSTIFICACION
public class MotivoCancelacionVacio implements IValidarCancelaciones {
    @Override
    public void validarCancelacion(GuardarDatosCancelaciones datosCancelaciones) {

        if(datosCancelaciones.motivoCancelacion() == null){
            throw new ValidacionDeDatos("Debe ingresar el motivo de la cancelacion");
        }
    }
}
