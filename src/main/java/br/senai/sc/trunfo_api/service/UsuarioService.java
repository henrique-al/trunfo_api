package br.senai.sc.trunfo_api.service;

import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario createOrUpdate(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> readAll() {
        return usuarioRepository.findAll();
    }

    public Usuario read(Long id) {
        return usuarioRepository.findById(id).orElseThrow();
    }

//    public Usuario readByName(String name, String passwd) {
//        Usuario user = usuarioRepository.findByName(name);
//        if (user != null && user.getSenha().equals(passwd)) {
//            return user;
//        }
//        throw new SecurityException("Senha incorreta");
//    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
