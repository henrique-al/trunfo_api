package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.Entity.Carta;
import br.senai.sc.trunfo_api.service.JogoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/game")
public class JogoController {
    private final JogoService jogoService;

    @GetMapping("/play")
    public ResponseEntity<List<Carta>> iniciarJogo(){
        return ResponseEntity.status(HttpStatus.OK).body(jogoService.embaralharCartas());
    }
}
