package br.senai.sc.trunfo_api.service;

import br.senai.sc.trunfo_api.model.DTO.GameDTO;
import br.senai.sc.trunfo_api.model.Entity.Carta;
import br.senai.sc.trunfo_api.model.Entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class JogoService {
    private final CartaService cartaService;
    private final UsuarioService usuarioService;

    public List<List<Carta>> embaralharCartas(){
        List<Carta> cartas = cartaService.readAll();
        Collections.shuffle(cartas);
        if (cartas.size() < 10){
            throw new RuntimeException("Size too small");
        }
        System.out.println(cartas);
        return List.of(cartas.subList(0, 5), cartas.subList(5, 10));
    }

    public String randomAttr(){
        List<Field> fields = List.of(Carta.class.getDeclaredFields());
        List<String> fieldNames = new ArrayList<>();
        for (Field field:fields) {
            if (!(field.getName().equals("id")||field.getName().equals("nome"))){
                fieldNames.add(field.getName());
            }
        }
        int random = new Random().nextInt(fieldNames.size());
        return fieldNames.get(random);
    }

    public GameDTO play(Long u1, Long u2){
        List<List<Carta>> cartas = embaralharCartas();
        List<Carta> player = cartas.get(0);
        List<Carta> pc = cartas.get(1);

        GameDTO gameDTO = new GameDTO(usuarioService.read(u1),
                usuarioService.read(u2),
                new Integer[]{0, 0},
                new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            Integer[] placar = gameDTO.getPlacar();
            try{
                String atributo = randomAttr();
                Field attrField = Carta.class.getDeclaredField(atributo);
                attrField.setAccessible(true);
                Object playerValue = attrField.get(player.get(i));
                Object pcValue = attrField.get(pc.get(i));
                gameDTO.getAttrs().add(atributo);
                if ((int) playerValue > (int) pcValue) {
                    placar[0]++;
                } else if ((int) playerValue < (int) pcValue) {
                    placar[1]++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            gameDTO.setPlacar(placar);
        }

        return gameDTO;
    }
}
