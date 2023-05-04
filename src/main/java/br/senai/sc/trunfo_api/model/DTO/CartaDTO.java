package br.senai.sc.trunfo_api.model.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaDTO {
    @NotNull
    private String nome;
    @NotNull
    private Integer populacao;
    @NotNull
    private Integer area;
    @NotNull
    @Min(0)
    @Max(1)
    private Double idh;
    @NotNull
    private Integer pib;
    @NotNull
    private Integer turista;
}
