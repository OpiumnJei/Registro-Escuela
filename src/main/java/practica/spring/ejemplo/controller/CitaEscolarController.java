package practica.spring.ejemplo.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolarRepository;
import practica.spring.ejemplo.domain.citaEscolar.CitaEscolarService;
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
//        System.out.println(datosCita);

        //guardarmos los datos enviados por cliente(postman)
        service.guardarCitaEscolar(datosCita);
        return  ResponseEntity.ok(new RespuestaCitaEscolar(null, null,null,null,null));
//        return ResponseEntity.ok().build();
    }

}
