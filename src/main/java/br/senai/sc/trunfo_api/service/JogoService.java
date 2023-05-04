package br.senai.sc.trunfo_api.service;

import br.senai.sc.trunfo_api.model.Entity.Carta;
import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.model.VO.Placar;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class JogoService {
    private final UsuarioService usuarioService;
    private final CartaService cartaService;

    public Placar compararCartas(Long id1, Long id2){
        Usuario u1 = usuarioService.read(id1);
        Usuario u2 = usuarioService.read(id2);
        embaralharCartas(u1, u2);
        int vitoria1 = 0;
        int vitoria2 = 0;
        for (int i = 0; i < u1.getCartas().size(); i++) {
            Carta carta1 = u1.getCartas().get(i);
            Carta carta2 = u2.getCartas().get(i);
            int pontos=0;
            pontos = carta1.getArea()>carta2.getArea()? pontos+1:pontos-1;
            pontos = carta1.getPib()>carta2.getPib()? pontos+1:pontos-1;
            pontos = carta1.getTurista()>carta2.getTurista()? pontos+1:pontos-1;
            pontos = carta1.getIdh()>carta2.getIdh()? pontos+1:pontos-1;
            pontos = carta1.getPopulacao()>carta2.getPopulacao()? pontos+1:pontos-1;
            if (pontos > 0){
                vitoria1++;
            } else {
                vitoria2++;
            }
        }
        if (vitoria1>vitoria2){
            u1.setVitorias(u1.getVitorias()+1);
            usuarioService.createOrUpdate(u1);
            return new Placar(u1.getNome(), u2.getNome(), vitoria1, vitoria2);
        }
        return new Placar(u2.getNome(), u1.getNome(), vitoria2, vitoria1);
    }

    public void embaralharCartas(Usuario u1, Usuario u2){
        List<Carta> cartas = cartaService.readAll();
        Collections.shuffle(cartas);
        u1.setCartas(cartas.subList(0, cartas.size()/2));
        u2.setCartas(cartas.subList(cartas.size()/2, cartas.size()));
    }
}
