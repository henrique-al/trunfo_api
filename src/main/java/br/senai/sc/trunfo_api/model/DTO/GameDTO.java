package br.senai.sc.trunfo_api.model.DTO;

import br.senai.sc.trunfo_api.model.Entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private Usuario u1;
    private Usuario u2;
    private Integer[] placar;
    private List<String> attrs;
}
