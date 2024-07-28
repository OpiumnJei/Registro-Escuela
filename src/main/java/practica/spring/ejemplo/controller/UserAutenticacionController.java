package practica.spring.ejemplo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.spring.ejemplo.domain.Users.User;
import practica.spring.ejemplo.domain.Users.UserDTO;
import practica.spring.ejemplo.dto.TokenJwtDTO;
import practica.spring.ejemplo.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class UserAutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping

    public ResponseEntity autenticarUsuario(@RequestBody @Valid UserDTO userDTO){

        //autenticamos los datos del usuario DTO con su login = usuario y la contrasenia
        Authentication tokenAutenticacion = new UsernamePasswordAuthenticationToken(userDTO.login(), userDTO.contrasenia());

        var usuarioAutenticado = authenticationManager.authenticate(tokenAutenticacion);
        var tokenJWT = tokenService.generarTokenJWT((User) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }
}
