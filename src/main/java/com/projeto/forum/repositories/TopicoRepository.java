package com.projeto.forum.repositories;

import com.projeto.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByCursoNome(String nomeCurso, Pageable pageable); //Gera a query

    //criando minha própria Query sem usar o padrão do JpaRepository
    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso") //JPQL
    List<Topico> carregandoCursoNome(@Param("nomeCurso") String nomeCurso);
}
