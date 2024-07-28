package practica.spring.ejemplo.domain.profesor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "profesores")
@Entity(name = "Profesor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String asignatura_impartida;
    private String correo;
    private String telefono;
    private Boolean activo;

    //metod para guardar profesores en la base de datos
    public Profesor(ProfesorDTO datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.asignatura_impartida = datos.asignatura_impartida();
        this.correo = datos.correo();
        this.telefono = datos.telefono();
    }

    //metodo para actualizar los datos de un profesor en la bd
    public void actualizarProfesor(ActualizarDatosProfesor actualizarDatosProfesor){
        if(actualizarDatosProfesor.id() != null){
            this.id = actualizarDatosProfesor.id();
        }
        if(actualizarDatosProfesor.nombre() != null){
            this.nombre = actualizarDatosProfesor.nombre();
        }
        if(actualizarDatosProfesor.asignatura_impartida() != null){
            this.asignatura_impartida = actualizarDatosProfesor.asignatura_impartida();
        }
        if(actualizarDatosProfesor.correo() != null){
            this.correo = actualizarDatosProfesor.correo();
        }
        if(actualizarDatosProfesor.telefono() != null){
            this.telefono = actualizarDatosProfesor.telefono();
        }
    }

    //metodo para borrar(desactivar) un profesor - delete logico
    public void desactivarProfesor(){
        this.activo = false;
    }

    public String getAsignatura() {
        return asignatura_impartida;
    }

    public String getNombre() {
        return nombre;
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

}


