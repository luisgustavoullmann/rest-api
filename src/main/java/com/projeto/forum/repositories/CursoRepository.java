package com.projeto.forum.repositories;

import com.projeto.forum.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nomeCurso);
}
