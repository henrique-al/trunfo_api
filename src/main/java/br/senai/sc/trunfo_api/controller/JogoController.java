package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.model.VO.Placar;
import br.senai.sc.trunfo_api.service.JogoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/game")
public class JogoController {
    private final JogoService jogoService;

    @GetMapping("/play/{id1}/{id2}")
    public ResponseEntity<Placar> iniciarJogo(@PathVariable Long id1, @PathVariable Long id2){
        return ResponseEntity.status(HttpStatus.OK).body(jogoService.compararCartas(id1, id2));
    }
}
