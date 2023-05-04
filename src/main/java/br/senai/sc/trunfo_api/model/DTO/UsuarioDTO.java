package br.senai.sc.trunfo_api.model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @NotNull
    private String nome;
    @NotNull
    private String senha;
    private String foto;
}
