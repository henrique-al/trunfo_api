package br.senai.sc.trunfo_api.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private String senha;
    private Integer vitorias;
    private Integer derrotas;
    @Column(length = 400)
    private String foto;
    @OneToMany
    @JoinColumn(name = "id_player")
    private List<Carta> cartas;
}
