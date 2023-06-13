package br.senai.sc.trunfo_api.repository;

import br.senai.sc.trunfo_api.model.Entity.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, String> {
}
