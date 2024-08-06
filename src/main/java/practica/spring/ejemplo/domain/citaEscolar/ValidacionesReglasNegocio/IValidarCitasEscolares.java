package practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio;

import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;

//esta interfaz sirve solo de esqueleto, declara el metodo, mas no dice como debe implementarse
public interface IValidarCitasEscolares {
    //metodo para las validaciones(reglas de negocio)
     void validar(GuardarDatosCitaEscolar datosCitaEscolar);
}
