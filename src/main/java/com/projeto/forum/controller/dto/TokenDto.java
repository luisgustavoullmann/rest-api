package com.projeto.forum.controller.dto;

import lombok.Getter;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@Getter
public class TokenDto {

    private String token;
    private String tipo;

    public TokenDto(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }
}
