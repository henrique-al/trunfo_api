package br.senai.sc.trunfo_api.utils;

import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.repository.UsuarioRepository;
import br.senai.sc.trunfo_api.security.model.entity.UsuarioSecurity;
import br.senai.sc.trunfo_api.security.model.enums.Perfil;
import br.senai.sc.trunfo_api.security.repository.SecurityRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BancoUtil {
    private UsuarioRepository usuarioRepository;
    private SecurityRepository securityRepository;

    @PostConstruct
    public void popularBanco(){
        usuarioRepository.deleteAll();
        Usuario admin = new Usuario();
        UsuarioSecurity adminSecurity = new UsuarioSecurity(1L,
                List.of(Perfil.ADMIN),
                "admin",
                "admin",
                true,
                true,
                true,
                true);
        admin.setVitorias(0);
        admin.setDerrotas(0);
        admin.setFoto("https://i.pinimg.com/originals/0f/6e/2a/0f6e2a1a3a5b5b0b0a0b0b0b0a0b0b0b.jpg");
        admin.setSecurity(adminSecurity);

        Usuario padrao = new Usuario();
        UsuarioSecurity usuarioSecurity = new UsuarioSecurity(1L,
                List.of(Perfil.USUARIO),
                "padrao",
                "padrao",
                true,
                true,
                true,
                true);
        padrao.setVitorias(0);
        padrao.setDerrotas(0);
        padrao.setFoto("https://i.pinimg.com/originals/0f/6e/2a/0f6e2a1a3a5b5b0b0a0b0b0b0a0b0b0b.jpg");
        padrao.setSecurity(usuarioSecurity);

        securityRepository.saveAll(List.of(adminSecurity, usuarioSecurity));
        usuarioRepository.saveAll(List.of(admin, padrao));
    }

}
