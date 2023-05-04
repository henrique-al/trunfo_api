package br.senai.sc.trunfo_api.model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Placar {
    private String ganhador;
    private String perdedor;
    private Integer ganhadorPontos;
    private Integer perdedorPontos;
}
