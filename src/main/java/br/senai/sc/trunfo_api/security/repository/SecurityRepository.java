package br.senai.sc.trunfo_api.security.repository;

import br.senai.sc.trunfo_api.security.model.entity.UsuarioSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<UsuarioSecurity, Long> {
    UsuarioSecurity findByUsername(String username);
}
