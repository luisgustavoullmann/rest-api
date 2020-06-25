package com.projeto.forum.controller.dto;

import com.projeto.forum.modelo.Curso;
import com.projeto.forum.modelo.Topico;
import com.projeto.forum.repositories.CursoRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicoPostDto { //Post Dto que recebe
    @NotNull
    @NotEmpty
    private String titulo;
    @NotNull
    @NotEmpty
    private String mensagem;
    @NotNull
    @NotEmpty
    @Length(min = 1, max = 100)
    private String nomeCurso; //não recebe a classe de domain

    public Topico converter(CursoRepository repository) { //Post - passando o repository do curso, para poder salvar o nome do curso junto ao topico
        Curso curso = repository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }
}
