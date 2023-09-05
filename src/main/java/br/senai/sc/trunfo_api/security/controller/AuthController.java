package br.senai.sc.trunfo_api.security.controller;

import br.senai.sc.trunfo_api.security.model.dto.LoginDTO;
import br.senai.sc.trunfo_api.security.model.entity.UsuarioSecurity;
import br.senai.sc.trunfo_api.security.repository.SecurityRepository;
import br.senai.sc.trunfo_api.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/log")
public class AuthController {
    private final SecurityRepository repository;
    private AuthenticationManager authenticationManager;

    @PostMapping("/in")
    public ResponseEntity<?> login(@RequestBody LoginDTO login, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                    login.getUsername(),
                    login.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(upat);
            if (authentication.isAuthenticated()) {
                UsuarioSecurity usuario = (UsuarioSecurity) authentication.getPrincipal();
                Cookie cookie = CookieUtil.gerarCookie(usuario);
                response.addCookie(cookie);
                return ResponseEntity.status(HttpStatus.OK).body(authentication.getPrincipal());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
