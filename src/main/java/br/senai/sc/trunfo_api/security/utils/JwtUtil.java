package br.senai.sc.trunfo_api.security.utils;

import br.senai.sc.trunfo_api.security.model.entity.UsuarioSecurity;
import br.senai.sc.trunfo_api.security.repository.SecurityRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static SecurityRepository repository;

    @Autowired
    public JwtUtil(SecurityRepository repository) {
        JwtUtil.repository = repository;
    }

    private static final String SENHA_FORTE =
            "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";

    public static String gerarToken(UsuarioSecurity user) {
        Algorithm algorithm = Algorithm.HMAC256(SENHA_FORTE);
        return JWT.create()
                .withIssuer("WEG")
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime()+60*30))
                .sign(algorithm);
    }

    public static UsuarioSecurity getUsuario(String token) {
        String username = JWT.decode(token).getSubject();
        return repository.findByUsername(username);
    }
}
