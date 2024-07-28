package practica.spring.ejemplo.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import practica.spring.ejemplo.domain.Users.UserRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,  @NotNull FilterChain filterChain) throws ServletException, IOException {
        var headerAuthoritation = request.getHeader("Authorization");
        if (headerAuthoritation != null){
            var token = headerAuthoritation.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token);
//            System.out.println(tokenService.getSubject(token));
            if(nombreUsuario != null){
                var usuarioValido = userRepository.findBylogin(nombreUsuario);
                var autenticacionUsuario = new UsernamePasswordAuthenticationToken(usuarioValido, null,
                        usuarioValido.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacionUsuario);
            }
        }
        filterChain.doFilter(request, response);
    }
}
