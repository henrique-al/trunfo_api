package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.DTO.UsuarioDTO;
import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        return ResponseEntity.status(201).body(usuarioService.createOrUpdate(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioNovo) {
        Usuario usuarioExistente = usuarioService.read(id);
        BeanUtils.copyProperties(usuarioNovo, usuarioExistente);
        return ResponseEntity.status(200).body(usuarioService.createOrUpdate(usuarioExistente));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> readAll() {
        return ResponseEntity.status(200).body(usuarioService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> read(@PathVariable Long id) {
        return ResponseEntity.status(200).body(usuarioService.read(id));
    }

//    @GetMapping("/{nome}/{senha}")
//    public ResponseEntity<Usuario> readByName(@PathVariable String nome, @PathVariable String senha) {
//        return ResponseEntity.status(200).body(usuarioService.readByName(nome, senha));
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }

}
