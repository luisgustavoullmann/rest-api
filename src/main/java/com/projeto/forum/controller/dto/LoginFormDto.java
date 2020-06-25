package com.projeto.forum.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@Setter
@Getter
public class LoginFormDto {
    private String email;
    private String senha;

    public UsernamePasswordAuthenticationToken converter() {//dadosLogin
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
