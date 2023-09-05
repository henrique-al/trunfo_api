package br.senai.sc.trunfo_api.security.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {
    ADMIN, USUARIO;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
