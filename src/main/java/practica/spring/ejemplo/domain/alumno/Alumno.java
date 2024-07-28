package practica.spring.ejemplo.domain.alumno;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

    @Table(name = "alumnos")
    @Entity(name = "Alumno")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;
    private String correo;
    private String telefono;
    private String grado;
    private Boolean activo;

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getGrado() {
        return grado;
    }

    public Alumno(AlumnoDTO datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.apellido = datos.apellido();
        this.matricula = datos.matricula();
        this.correo = datos.correo();
        this.telefono = datos.telefono();
        this.grado = datos.grado();

    }

    public void actualizarDatosALumno(ActualizarAlumno actualizarAlumno){
        if(actualizarAlumno.nombre() != null){
            this.nombre = actualizarAlumno.nombre();
        }
        if (actualizarAlumno.matricula() != null){
            this.matricula = actualizarAlumno.matricula();
        }


    }

    public void desactivarEstudiante() {
        this.activo = false;

    }
}
