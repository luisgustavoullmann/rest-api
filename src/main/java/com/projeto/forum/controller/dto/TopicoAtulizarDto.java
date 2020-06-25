package com.projeto.forum.controller.dto;

import com.projeto.forum.modelo.Topico;
import com.projeto.forum.repositories.TopicoRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicoAtulizarDto { //Só pode atualizar esses dois campos
    @NotNull
    @NotEmpty
    private String titulo;
    @NotNull
    @NotEmpty
    private String mensagem;

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        //Acha o topico pelo id, e atualiza/set com o this
        Topico topico = topicoRepository.getOne(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
