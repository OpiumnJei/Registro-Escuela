package practica.spring.ejemplo.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamientoExcepciones {

    //tratar excepcion a nivel de cliente para que en lugar de retornar un 500(un error interno en el sv), nos retorne
    // un correspondiente 404(no encontrado) indicando que el error es del cliente mas no del servidor
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    //error 400 quiere decir que hay un error en el lado del cliente a la hora de llenar los campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors()
                .stream()
                .map(DatosErrorValidacion::new).toList();

        return ResponseEntity.badRequest().body(errores);


    }
    //Metodo para tratar(retornar) las validaciones relaciondas con la integridad(validez) de los datos enviados
    @ExceptionHandler(ValidacionDeDatos.class)
    public ResponseEntity tratarErrorValidacionDatos(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //Metodo para tratar(retornar) las validaciones relacionadas a las reglas de negocio
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity tratarValidacionReglasNegocio(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //record para mapear errores
    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}


