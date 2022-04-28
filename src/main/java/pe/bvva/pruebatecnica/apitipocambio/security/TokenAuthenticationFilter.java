package pe.bvva.pruebatecnica.apitipocambio.security;

import lombok.extern.slf4j.Slf4j;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.UsuarioEntity;
import pe.bvva.pruebatecnica.apitipocambio.repositories.UsuarioRepository;
import pe.bvva.pruebatecnica.apitipocambio.services.UsuarioService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(httpServletRequest);
        try {
            if (StringUtils.hasText(jwt) && usuarioService.validateToken(jwt)) {
                String usuarioUsername = usuarioService.getUsernameFromToken(jwt);
                UsuarioEntity usuario = usuarioRepository.findByUsername(usuarioUsername)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario"));
                UserPrincipal principal = UserPrincipal.create(usuario);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            log.error("Error al autenticar al usuario",e);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    public String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText((bearerToken)) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
