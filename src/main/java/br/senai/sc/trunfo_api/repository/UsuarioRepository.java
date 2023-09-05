package br.senai.sc.trunfo_api.repository;

import br.senai.sc.trunfo_api.model.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
//    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome")
//    Usuario findByName(@Param("nome") String nome);
}
