package br.senai.sc.trunfo_api.service;

import br.senai.sc.trunfo_api.model.Entity.Carta;
import br.senai.sc.trunfo_api.model.Entity.Imagem;
import br.senai.sc.trunfo_api.repository.CartaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartaService{
    private final CartaRepository cartaRepository;

    public Carta createOrUpdate(Carta carta){
        return cartaRepository.save(carta);
    }

    public List<Carta> readAll(){
        return cartaRepository.findAll();
    }

    public Carta read(Long id){
        return cartaRepository.findById(id).orElseThrow();
    }

    public void delete(Long id){
        cartaRepository.deleteById(id);
    }
}
