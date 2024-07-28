package practica.spring.ejemplo.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;
import practica.spring.ejemplo.domain.alumno.*;

import java.net.URI;

@RestController
@RequestMapping("/registro")
public class AlumnoController {

    //inyeccion
    @Autowired
    private AlumnoRepository repository;

    //CREAR UN REGISTRO DE ESTUDIANTE
    @PostMapping
    public ResponseEntity<RespuestaAlumno> registrarEstudiante(@RequestBody @Valid AlumnoDTO datos, @NotNull UriComponentsBuilder uriComponentsBuilder) {
        Alumno alumno = repository.save(new Alumno(datos));
        RespuestaAlumno respuestaAlumno = new RespuestaAlumno(alumno.getId(), alumno.getNombre(), alumno.getApellido(),
                alumno.getMatricula(), alumno.getCorreo(), alumno.getTelefono(), alumno.getGrado());

        URI url = uriComponentsBuilder.path("/registro/{id}").buildAndExpand(alumno.getId()).toUri();
        return ResponseEntity.created(url).body(respuestaAlumno);
    }

//    @GetMapping
//    public List<ListaAlumnos> listadoAlumnos(){
//        return repository.findAll().stream().map(ListaAlumnos::new).toList();
////        return repository.findAll();
//    }

    //OBTIENE TODOS LOS REGISTROS ACTIVOS DE ESTUDIANTES
    //para la paginacion
    @GetMapping
    public ResponseEntity<Page<ListaAlumnos>> listadoAlumnos(@PageableDefault Pageable paginacion) {

        //retorna alumnos sin tomar en cuenta si estan activos o no
//        return repository.findAll(paginacion).map(ListaAlumnos::new);

        //retorna solamente alumnos activos
        return ResponseEntity.ok(repository.findByActivoTrue(paginacion).map(ListaAlumnos::new));


        //retorna todos los alumnos
        /* return repository.findAll(); */
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaAlumno> retornarAlumnoEspecifico(@PathVariable Long id){
        Alumno alumno = repository.getReferenceById(id);
        var datosALumno = new RespuestaAlumno(alumno.getId(), alumno.getNombre(), alumno.getApellido(),
                alumno.getMatricula(), alumno.getCorreo(), alumno.getTelefono(), alumno.getGrado());

        return ResponseEntity.ok(datosALumno);

    }

    //ACTUALIZAR EL REGISTRO DE UN ALUMNO
    @PutMapping
    @Transactional
    public ResponseEntity actualizarAlumno(@RequestBody @Valid ActualizarAlumno actualizarAlumno) {
        Alumno alumno = repository.getReferenceById(actualizarAlumno.id());
        alumno.actualizarDatosALumno(actualizarAlumno);
        return ResponseEntity.ok(new RespuestaAlumno(alumno.getId(), alumno.getNombre(), alumno.getApellido(),
                alumno.getMatricula(), alumno.getCorreo(), alumno.getTelefono(), alumno.getGrado()));
    }

    //DESACTIVAR UN ESTUDIANTE MEDIANTE ID
    //Delete logico, desactivar un alumno(no eliminarlo de la base de datos)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarAlumno(@PathVariable Long id) {
        Alumno alumno = repository.getReferenceById(id);
        alumno.desactivarEstudiante();
        return ResponseEntity.noContent().build();
    }


//    //Eliminar directamente de la base de datos
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarAlumno(@PathVariable Long id){
//        Alumno alumno = repository.getReferenceById(id);
//        repository.delete(alumno);
//    }


}
