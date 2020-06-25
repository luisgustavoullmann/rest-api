package com.projeto.forum.modelo;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Perfil implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome; //Admin, moderador etc..

    @Override
    public String getAuthority() {
        return nome; //qual atributo representa authority
    }
}
