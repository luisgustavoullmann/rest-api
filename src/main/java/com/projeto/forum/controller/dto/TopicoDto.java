package com.projeto.forum.controller.dto;

import com.projeto.forum.modelo.Topico;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicoDto { //Controla quais campos quero devolver no endpoint
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) { // Construtor para fazer o map() abaixo
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static Page<TopicoDto> listTopico(Page<Topico> topicos) { //alterando aonde era List para Page
        //List - alterar os PAGE para list, se quiser retornar tudo.
        //Essa linha substitui o forEach para os elementos da list
        //return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());

        //Page
        return topicos.map(TopicoDto::new);
    }
}
