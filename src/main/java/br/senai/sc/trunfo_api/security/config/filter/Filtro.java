package br.senai.sc.trunfo_api.security.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class Filtro extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (rotaPrivada(request.getRequestURI())){

        }
        filterChain.doFilter(request, response);
    }

    private boolean rotaPrivada(String url){
        return url.contains("/auth");
    }
}
