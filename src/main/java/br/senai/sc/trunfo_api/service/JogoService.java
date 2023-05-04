package br.senai.sc.trunfo_api.service;

import br.senai.sc.trunfo_api.model.Entity.Carta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class JogoService {
    private final CartaService cartaService;

    public List<Carta> embaralharCartas(){
        List<Carta> cartas = cartaService.readAll();
        Collections.shuffle(cartas);
        return cartas;
    }
}
