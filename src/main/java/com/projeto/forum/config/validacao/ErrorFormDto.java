package com.projeto.forum.config.validacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorFormDto {
    private String campo;
    private String erro;
}
