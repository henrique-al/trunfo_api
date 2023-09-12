package br.senai.sc.trunfo_api.utils;

import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.repository.UsuarioRepository;
import br.senai.sc.trunfo_api.security.model.entity.UsuarioSecurity;
import br.senai.sc.trunfo_api.security.model.enums.Perfil;
import br.senai.sc.trunfo_api.security.repository.SecurityRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BancoUtil {
    private SecurityRepository securityRepository;

    @PostConstruct
    public void popularBanco(){
        securityRepository.deleteAll();
        Usuario admin = new Usuario();
        admin.setVitorias(0);
        admin.setDerrotas(0);
        admin.setFoto("https://i.pinimg.com/originals/0f/6e/2a/0f6e2a1a3a5b5b0b0a0b0b0b0a0b0b0b.jpg");

        Usuario padrao = new Usuario();
        padrao.setVitorias(0);
        padrao.setDerrotas(0);
        padrao.setFoto("https://i.pinimg.com/originals/0f/6e/2a/0f6e2a1a3a5b5b0b0a0b0b0b0a0b0b0b.jpg");

        UsuarioSecurity usuarioSecurity = new UsuarioSecurity(
                List.of(Perfil.USUARIO),
                "padrao",
                new BCryptPasswordEncoder().encode("padrao"),
                true,
                true,
                true,
                true
                ,padrao);

        UsuarioSecurity adminSecurity = new UsuarioSecurity(
                List.of(Perfil.ADMIN),
                "admin",
                new BCryptPasswordEncoder().encode("admin"),
                true,
                true,
                true,
                true,
                admin);

        securityRepository.saveAll(List.of(adminSecurity, usuarioSecurity));
    }

}
