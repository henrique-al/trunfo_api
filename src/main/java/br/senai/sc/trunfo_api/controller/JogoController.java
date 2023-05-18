package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.DTO.GameDTO;
import br.senai.sc.trunfo_api.model.Entity.Carta;
import br.senai.sc.trunfo_api.service.JogoService;
import br.senai.sc.trunfo_api.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/game")
@CrossOrigin
public class JogoController {
    private final JogoService jogoService;

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<GameDTO> iniciarJogo(@PathVariable Long id1, @PathVariable Long id2){
        return ResponseEntity.status(HttpStatus.OK).body(jogoService.play(id1, id2));
    }
}
