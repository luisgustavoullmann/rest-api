package com.projeto.forum.repositories;

import com.projeto.forum.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); //usa o findBy do Jpa com o attr email do usuario
}
