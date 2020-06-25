package com.projeto.forum.config.security;

import com.projeto.forum.modelo.Usuario;
import com.projeto.forum.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@Service
//@RequiredArgsConstructor - não usar
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository; //Cria o repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
        if(usuario.isPresent()){
            return usuario.get(); //Se estiver presente (por isso o Optional), get usuario.
        }
        throw new UsernameNotFoundException("Dados inválidos.");
    }
}
