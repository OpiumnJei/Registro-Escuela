package practica.spring.ejemplo.domain.citaEscolar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practica.spring.ejemplo.domain.alumno.AlumnoRepository;
import practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio.IValidarCancelaciones;
import practica.spring.ejemplo.domain.citaEscolar.ValidacionesReglasNegocio.IValidarCitasEscolares;
import practica.spring.ejemplo.domain.profesor.Profesor;
import practica.spring.ejemplo.domain.profesor.ProfesorRepository;
import practica.spring.ejemplo.infra.errores.ValidacionDeDatos;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaEscolarService {

    //inyecciones
    @Autowired
    private CitaEscolarRepository citaEscolarRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private CancelarCitaEscolarRepository cancelarCitaEscolarRepository;

    //inyeccion de todas las validaciones hechas
    @Autowired
    List<IValidarCitasEscolares> validaciones;
    @Autowired
    List<IValidarCancelaciones> validarCancelaciones;

    //Guardar citas y verificar validaciones
    public RespuestaCitaEscolar guardarCitaEscolar(GuardarDatosCitaEscolar  datosCitaEscolar){
        ///comprobamos que el id del alumno se encuentre en la base de datos
        //de lo contrario lanzara una runtimeException(ValidacionDeDatos)
        if(!alumnoRepository.findById(datosCitaEscolar.idAlumno()).isPresent()){
            throw new ValidacionDeDatos("El id del alumno no fue encontrado");
        }

        //comprobamos que el id introducido por el cliente(postman) no sea nulo, y que exista en la
        // base de datos de lo contrario lanzara una runtimeException(ValidacionDeDatos)
        if(datosCitaEscolar.idProfesor() != null && !profesorRepository.existsById(datosCitaEscolar.idProfesor())){
            throw new ValidacionDeDatos("El id del profesor no fue encontrado");
        }

        //Valida los datos enviados por el usuario(postman)/los valida(los filtra)
        validaciones.forEach(v -> v.validar(datosCitaEscolar));

        //seleccionamos un profesor aleatorio
          var profesor = seleccionarProfesorAleatorio(datosCitaEscolar);

        //buscamos un alumno en la bd pasando como parametro el id enviado desde el cliente(postman)
        var alumno = alumnoRepository.findById(datosCitaEscolar.idAlumno()).get();

        //creamos una nueva cita usando los datos encontrados en la base de datos
        //que fueron enviandos por el cliente(postman) en formato json y (lo mapeamos a la entidad = CitaEscolar)
        var citaEscolar = new CitaEscolar(null, alumno, profesor, datosCitaEscolar.tutorAlumno(),datosCitaEscolar.fecha());

        //guardarmos una nueva citaEscolar en la base de datos
        citaEscolarRepository.save(citaEscolar);

        //retornar los datos al cliente despues de creada una cita
        return new RespuestaCitaEscolar(citaEscolar);
    }

    //metodo que buscar un profesor aleatorio
    private Profesor seleccionarProfesorAleatorio(GuardarDatosCitaEscolar datosCitaEscolar) {
        //corroborar que el id no sea nulo
        if(datosCitaEscolar.idProfesor() != null){
            return profesorRepository.getReferenceById(datosCitaEscolar.idProfesor());
        }
        //si la asignatura impartida introducida es nula
        if (datosCitaEscolar.asignaturaImpartida() == null) {
            throw new ValidacionDeDatos("debe seleccionarse una asignatura para el profesor");
        }

        //metodo para buscar un profesor que coincida con la asignatura enviada por el cliente
        return profesorRepository.SeleccionarProfesorConAsignaturaEnFecha(datosCitaEscolar.asignaturaImpartida(), datosCitaEscolar.fecha());
    }

    //metodo para cancelar una citaEscolar
    public void cancelarCitaEscolar(GuardarDatosCancelaciones datosCancelaciones){

        //validar los datos enviados
        validarCancelaciones.forEach(vc ->vc.validarCancelacion(datosCancelaciones));
        //traer de la base de datos el id de la cita
        var cita = citaEscolarRepository.findById(datosCancelaciones.idCita()).get();
        //fecha en la que se esta creando la cancelacion
        var ahora = LocalDateTime.now();

        var datosCancelacion = new CancelarCitaEscolar(null, cita, datosCancelaciones.motivoCancelacion(),ahora);
        //guardamos en la bd
        cancelarCitaEscolarRepository.save(datosCancelacion);
    }
}
