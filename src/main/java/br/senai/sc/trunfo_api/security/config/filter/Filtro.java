package br.senai.sc.trunfo_api.security.config.filter;

import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.security.model.entity.UsuarioSecurity;
import br.senai.sc.trunfo_api.security.utils.CookieUtil;
import br.senai.sc.trunfo_api.security.utils.JwtUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class Filtro extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!rotaPrivada(request.getRequestURI())){
            try{
                String token = CookieUtil.buscarCookie(request);
                UsuarioSecurity usuario = JwtUtil.getUsuario(token);
                response.addCookie(CookieUtil.gerarCookie(usuario));
                Authentication authentication = new
                        UsernamePasswordAuthenticationToken(
                        usuario.getUsername(),
                        null,
                        usuario.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTDecodeException e){
                System.out.println("Token inválido");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (Exception e){
                System.out.println(e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean rotaPrivada(String url){
        return url.contains("/public") || url.contains("/log");
    }
}
