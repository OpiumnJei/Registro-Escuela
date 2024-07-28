package practica.spring.ejemplo.domain.profesor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

//record para almacenar los datos enviados en formato json por el usuario
public record ProfesorDTO(

        @NotBlank
        String nombre,

        @NotBlank
        String asignatura_impartida,

        @Email
        @NotBlank
        String correo,

        @NotBlank
        String telefono

) {
}
