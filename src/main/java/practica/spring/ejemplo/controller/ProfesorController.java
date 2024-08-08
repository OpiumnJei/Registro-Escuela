package practica.spring.ejemplo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import practica.spring.ejemplo.domain.profesor.*;

import java.net.URI;

@Tag(name = "Profesores", description = "Operaciones relacionadas con profesores")
@RestController
@RequestMapping("/registro/profesor")
@SecurityRequirement(name = "bearer-key")
public class ProfesorController {

     @Autowired
    private ProfesorRepository profeRepository;

     //registrar un profesor
     @Operation(summary = "Crear registro de profesor", description = "Se crea un nuevo registro de profesor.")
    @PostMapping
    public ResponseEntity<ResponseProfesor> registrarProfesor(@RequestBody @Valid ProfesorDTO datos, @NotNull UriComponentsBuilder uriComponentsBuilder) {
        Profesor profesor = profeRepository.save(new Profesor(datos));
        ///mapeamos el cuerpo de la respuesta que se mostrara al usuario una vez creado un registro
        ResponseProfesor responseProfesor = new ResponseProfesor(profesor.getId(), profesor.getNombre(),
                profesor.getAsignatura(), profesor.getCorreo(), profesor.getTelefono());

        //esta url apunta al registro creado recientemente, es decir, mostrara la informacion del nuevo registro
        URI url = uriComponentsBuilder.path("/registro/profesor/{id}").buildAndExpand(profesor.getId()).toUri();
        return ResponseEntity.created(url).body(responseProfesor);
//        return ResponseEntity.ok().build();
    }

    //listar profesores, si aun estan trabajando(activos)
    //PageableDefault(size = 4) indica la cantidad de registro que se mostraran por defecto
    @Operation(summary = "Obtener todos los registros de profesores activos", description = "Se obtienen los registros activos de profesores.")
    @GetMapping
    public ResponseEntity<Page<ListaProfesores>> listarProfesoresActivos(@PageableDefault(size = 4) Pageable paginacion){

        return ResponseEntity.ok(profeRepository.findByActivoTrue(paginacion).map(ListaProfesores::new));
    }

    //listar profesor en especifico
    @Operation(summary = "Obtener registro de profesor especifico", description = "Se obtiene registro de un profesor activo mediante id.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProfesor> listarProfesorEspecifico(@PathVariable Long id){
        //buscamos en la base de datos el id introducido en el path
        Profesor profesor = profeRepository.getReferenceById(id);
        //datos a retornar si se encuentra el maestro
        var datosRespuesta = new ResponseProfesor(profesor.getId(), profesor.getNombre(), profesor.getAsignatura(),
                profesor.getCorreo(), profesor.getTelefono());
        return ResponseEntity.ok(datosRespuesta);
    }

    //actualizar datos de un profesor en especifico
    @Operation(summary = "Actualizar datos de profesor especifico", description = "Se actualizan datos especificos de un profesor.")
    @PutMapping
    @Transactional
    public ResponseEntity actualizarDatosProfesor(@RequestBody @Valid ActualizarDatosProfesor actualizarDatosProfesor){
        //busca en la base de datos el id del profesor a actualizar
        Profesor profesor = profeRepository.getReferenceById(actualizarDatosProfesor.id());
        //se mapaean los datos a la clase profesor
        profesor.actualizarProfesor(actualizarDatosProfesor);
        //datos a retornar una vez actualizado el profesor
        return ResponseEntity.ok(new ResponseProfesor(profesor.getId(), profesor.getNombre(),
                profesor.getAsignatura(), profesor.getCorreo(), profesor.getTelefono()));
    }

    //eliminar(desactivar) profesor
    @Operation(summary = "Eliminar registro de profesor", description = "Se elimina el registro de un profesor mediante id")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivarProfesor(@PathVariable Long id){
        //busca el profesor en la base de datos
        Profesor profesor = profeRepository.getReferenceById(id);
        //hace un delete logico
        profesor.desactivarProfesor();
        return ResponseEntity.noContent().build();
    }



}
