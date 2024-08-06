package practica.spring.ejemplo.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolarService;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCancelaciones;
import practica.spring.ejemplo.domain.citaEscolar.RespuestaCitaEscolar;
import practica.spring.ejemplo.domain.citaEscolar.GuardarDatosCitaEscolar;

//alteranativa a la anotacion @RestController Controller y responsebody
@Controller
@ResponseBody
@RequestMapping("/citas")
public class CitaEscolarController {

    @Autowired
    private CitaEscolarService service;

    //Metodo para guardar una cita escolar
    @PostMapping
    @Transactional
    public ResponseEntity crearCitaEscolar(@RequestBody @Valid GuardarDatosCitaEscolar datosCita) {

        //guardarmos los datos enviados por cliente(postman)
       var clientResponse = service.guardarCitaEscolar(datosCita);

       //retorna los datos de la cita al cliente
        return  ResponseEntity.ok(clientResponse);
    }

    //Metodo para cancelar una cita

    @DeleteMapping("/cancelar")
    public ResponseEntity cancelarCitaEscolar(@RequestBody @Valid GuardarDatosCancelaciones datosCancelaciones){

        service.cancelarCitaEscolar(datosCancelaciones);

        return ResponseEntity.noContent().build();
    }

}
