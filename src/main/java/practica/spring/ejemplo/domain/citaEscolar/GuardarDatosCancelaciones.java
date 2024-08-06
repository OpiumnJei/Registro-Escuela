package practica.spring.ejemplo.domain.citaEscolar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record GuardarDatosCancelaciones(
        @NotNull
        Long idCita,
        @NotBlank
        String motivoCancelacion,

        LocalDateTime fecha
) {
}
