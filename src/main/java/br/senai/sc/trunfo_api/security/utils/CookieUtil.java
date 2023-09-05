package br.senai.sc.trunfo_api.security.utils;

import br.senai.sc.trunfo_api.security.model.entity.UsuarioSecurity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

public class CookieUtil {
    public static Cookie gerarCookie(UsuarioSecurity principal) {
        String token = JwtUtil.gerarToken(principal);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setMaxAge(60*30);
        return cookie;
    }

    public static String buscarCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "JWT");
        if (cookie != null){
            return cookie.getValue();
        }
        return null;
    }
}
