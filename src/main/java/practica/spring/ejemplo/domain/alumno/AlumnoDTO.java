package practica.spring.ejemplo.domain.alumno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AlumnoDTO(

        @NotBlank
        String nombre,

        @NotBlank
        String apellido,

        @NotBlank
        @Pattern(regexp = "\\d{4,10}")
        String matricula,

        @Email
        @NotBlank
        String correo,

        @NotBlank
        String telefono,

        @NotBlank
        String grado)

{


}
