package practica.spring.ejemplo.domain.citaEscolar;

import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "citas_canceladas")
@Entity(name = "Cancelaciones")
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CancelarCitaEscolar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = false)
    private CitaEscolar citaEscolar;


    @Column(name = "motivo_cancelacion", nullable = false)
    private String motivoCancelacion;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    public CancelarCitaEscolar(Long id, CitaEscolar citaEscolar, String motivoCancelacion, LocalDateTime fechaCancelacion) {
        this.id = id;
        this.citaEscolar = citaEscolar;
        this.motivoCancelacion = motivoCancelacion;
        this.fechaCancelacion = fechaCancelacion;
    }

    public CitaEscolar getCitaEscolar() {
        return citaEscolar;
    }

    public Long getId() {
        return id;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public LocalDateTime getFechaCancelacion() {
        return fechaCancelacion;
    }
}
