package com.projeto.forum.controller.dto;

import com.projeto.forum.modelo.StatusTopico;
import com.projeto.forum.modelo.Topico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalhesTopicoDto { //Dto com relacionamento com RespostaDto (domain: Topico, Resposta)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDto> respostas; //lista com as respostas do topico

    public DetalhesTopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.nomeAutor = topico.getAutor().getNome(); //Usuario
        this.status = topico.getStatus();
        this.respostas = new ArrayList<>(); //Retorna uma lista vazia de respostas
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
    }

}
