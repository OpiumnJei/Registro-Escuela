package practica.spring.ejemplo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolarService;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;
import practica.spring.ejemplo.domain.citaEscolar.RespuestaCitaEscolar;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/*PRUEBAS UNITARIAS:
* Junit
* Mockito
* JacksonTester
* */

@SpringBootTest //para trabajar en el contexto de spring
@AutoConfigureMockMvc //para hacer solicitudes http sin necesidad de iniciar un servidor
@AutoConfigureJsonTesters//Spring Boot configura automáticamente los JsonTester
class CitaEscolarControllerTest {

    //se crea una instancia de MockMvc para simular solicitudes http
    @Autowired
    private MockMvc mockMvc;

    //inyecciones
    @Autowired
    private JacksonTester<GuardarDatosCitaEscolar> guardarDatosCitaEscolarJacksonTester;

    @Autowired
    private JacksonTester<RespuestaCitaEscolar> respuestaCitaEscolarJacksonTester;

    @MockBean
    private CitaEscolarService citaEscolarService;

    @Test
    @DisplayName("deberia retornar un 400 bad request si los datos son invalidos")
    @WithMockUser //simula un usuario autenticado
    void crearCitaEscolarPrueba1() throws Exception {

        //given and when
        var response = mockMvc.perform(post("/citas")).andReturn().getResponse();

        //then
        //compara una respuesta del tipo bad request con el status retornado por response
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    @DisplayName("deberia retornar un 200 OK si los datos enviados son validos")
    @WithMockUser //simula un usuario autenticado
    void crearCitaEscolarPrueba2() throws Exception {

        //given
        var fecha = LocalDateTime.now().plusHours(1); //fecha futura
        var asignaturaImpartida = "Letras";
        var datosCitaEscolar = new RespuestaCitaEscolar(null, 1L, 1L, "juan felipe", fecha);

        //when
        //Cuando el método guardarCitaEscolar es llamado con cualquier argumento (any()), se devuelve el objeto datosCitaEscolar.
        when(citaEscolarService.guardarCitaEscolar(any())).thenReturn(datosCitaEscolar);

        //se emula una solicitud del tipo post al endpoint /citas
        var response = mockMvc.perform(post("/citas")
                //se especifica que el formato de la solicitud es un json
                .contentType(MediaType.APPLICATION_JSON)
                //se convierten los datos guardados a formato json y se retorna el tipo de respuesta http
                .content(guardarDatosCitaEscolarJacksonTester.write(new GuardarDatosCitaEscolar(null, 1L,1L,"juan felipe", fecha, asignaturaImpartida)).getJson())
        ).andReturn().getResponse();

        //then
        //compara una respuesta del tipo OK 200 con el status retornado por response
        assertEquals(HttpStatus.OK.value(),response.getStatus());

        //Convierte el objeto datosCitaEscolar a un JSON usando JacksonTester y lo almacena en la variable respuestaJson.
        var jsonEsperado = respuestaCitaEscolarJacksonTester.write(datosCitaEscolar).getJson();

        //compara el jsonEsperado con el json retornado en la respuesta de la solicitud http, espera que sean iguales
        assertEquals(jsonEsperado, response.getContentAsString());
    }
}