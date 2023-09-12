package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.DTO.CartaDTO;
import br.senai.sc.trunfo_api.model.Entity.Carta;
import br.senai.sc.trunfo_api.service.CartaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cards")
@CrossOrigin
public class CartaController {
    private final CartaService cartaService;

    @PostMapping
    public ResponseEntity<Carta> create(@RequestBody CartaDTO cartaDTO){
        Carta carta = new Carta();
        BeanUtils.copyProperties(cartaDTO, carta);
        return ResponseEntity.status(201).body(cartaService.createOrUpdate(carta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carta> update(@PathVariable Long id, @RequestBody CartaDTO cartaNova){
        Carta cartaExistente = cartaService.read(id);
        BeanUtils.copyProperties(cartaNova, cartaExistente);
        return ResponseEntity.status(200).body(cartaService.createOrUpdate(cartaExistente));
    }

    @GetMapping
    public ResponseEntity<List<Carta>> readAll(){
        return ResponseEntity.status(200).body(cartaService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carta> read(@PathVariable Long id){
        return ResponseEntity.status(200).body(cartaService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        cartaService.delete(id);
        return ResponseEntity.status(204).build();
    }
}
