package practica.spring.ejemplo.domain.profesor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import practica.spring.ejemplo.domain.alumno.Alumno;
import practica.spring.ejemplo.domain.alumno.AlumnoDTO;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolar;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.junit.jupiter.api.Assertions.*;

/*PRUEBAS UNITARIAS:
 * Junit
 * Mockito
 * JacksonTester
 * */

@DataJpaTest//para configurar una prueba enfocada en la capa de persistencia (JPA).
//para controlar la configuración automática de la base de datos utilizada en las pruebas
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//indicamos el perfil que estamos utilizando
@ActiveProfiles("test")
class ProfesorRepositoryTest {

    //para hacer las pruebas al repositorio
    @Autowired
    private ProfesorRepository profesorRepository;

    //version especializada de entityManager que sirve para hacer pruebas en la base de datos
    @Autowired
    private TestEntityManager em; //parte de la dependencia springboot-security-test

    @Test
    @DisplayName("En caso de que se encuentre un profesor en otra cita escolar en el mismo horario, deberia retornar nulo")
    void seleccionarProfesorConAsignaturaEnFechaPrueba1() {

        //given
        //indicamos fecha en la que se hara la cita, sera el proximo martes a las 9:00 AM
        var citaProximoMartes9Am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(9, 0);

        var profesor = registrarProfesor("ciencias", "juan1@gmail.com");
        var alumno = registrarALumno("fernandez", "1010010", "f1@gmail.com");
        registrarCitaEscolar(alumno, profesor, citaProximoMartes9Am);

        var asignaturaProfesor = "ciencias";
        //when
        var profesorLibre = profesorRepository.SeleccionarProfesorConAsignaturaEnFecha(asignaturaProfesor, citaProximoMartes9Am);

        //then
        //CLASES Y METODOS DE JUNIT
        //alternativa al metodo assertThat utilizado en versiones anteriores de Junit
        assertNull(profesorLibre);
    }

    @Test
    @DisplayName("Deberia retornar un profesor cuando se realice la consulta en la bd")
    void seleccionarProfesorConAsignaturaEnFechaPrueba2() {

        //given
        //indicamos fecha en la que se hara la cita, sera el proximo martes a las 9:00 AM
        var citaProximoMartes9Am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(9, 0);

        var profesor = registrarProfesor("ciencias", "juan1@gmail.com");

        //when
        var profesorLibre = profesorRepository.SeleccionarProfesorConAsignaturaEnFecha(profesor.getAsignatura(), citaProximoMartes9Am);

        //then
        //se compara un profesor con un profesor de la base de datos
        assertEquals(profesor, profesorLibre); //alternativa a assertThat(profesorLibre).isEqualsTo.(profesor)

    }

    @Test
    @DisplayName("Deberia retornar true si se encuentra un profesor activo")
    void findActivoByIdPrueba1(){

        //given
        var profesor = registrarProfesor("ciencias", "juan1@gmail.com");

        //when
        var profesorActivo = profesorRepository.findActivoById(profesor.getId());
        //then
        assertNotNull(profesorActivo);//se verifica que el valor devuelto no sea nulo, se retorne un bolean
        assertTrue(profesorActivo); //verifica que un profesor se encuentre activo, si esta activo retorna true

    }

    @Test
    @DisplayName("Deberia retornar false si el profesor no esta activo, si retorna false se cumple la asercion")
    void findActivoByIdPrueba2(){
        //given
        var profesor = registrarProfesor("ciencias", "juan1@gmail.com");
        profesor.desactivarProfesor(); //desactivamos el profesor
        //when
        var profesorActivo = profesorRepository.findActivoById(profesor.getId());
        //then
        assertNotNull(profesorActivo); //se verifica que el valor devuelto no sea nulo, se retorne un bolean
        assertFalse(profesorActivo);//verifica que un profesor se encuentre inactivo, por lo que si el retorno es false estaria en lo correcto

    }

    //METODOS INTERNOS PARA PRUEBAS
    //metodo para crear un nuevo registro de una cita
    private void registrarCitaEscolar(Alumno alumno, Profesor profesor, LocalDateTime fecha){
        em.persist(new CitaEscolar(null, alumno, profesor,"tutorTest", fecha));
    }

    //metodo que crea una instancia de profesor y guarda los datos(persist) en la bd
    private Profesor registrarProfesor(String asignatura_impartida, String correo) {
        var profesor = new Profesor(datosProfesor("juan felipe", asignatura_impartida, correo));
        em.persist(profesor);
        return profesor;
    }

    //metodo que crea una instancia de alumno y guarda los datos(persist) en la bd
    private Alumno registrarALumno(String apellido, String matricula, String correo) {
        var alumno = new Alumno(datosAlumnos("federico", apellido, matricula, correo));
        em.persist(alumno);
        return alumno;
    }

    //los datos de un alumno
    private AlumnoDTO datosAlumnos(String nombre, String apellido, String matricula, String correo){
        return new AlumnoDTO(nombre, apellido, matricula, correo, "800930939", "gradotest");
    }

  //los datos de un profesor
    private ProfesorDTO datosProfesor(String nombre, String asignatura_impartida, String correo){
        return new ProfesorDTO(nombre, asignatura_impartida , correo, "8008930939");
    }
}