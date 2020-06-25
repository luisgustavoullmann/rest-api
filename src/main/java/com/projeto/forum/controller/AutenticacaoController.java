package com.projeto.forum.controller;

import com.projeto.forum.config.security.TokenService;
import com.projeto.forum.controller.dto.LoginFormDto;
import com.projeto.forum.controller.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@RestController
@RequestMapping("/auth")//Solicitado quando o usuário fizer login
public class AutenticacaoController {//JJWT Autenticacao Controller

    @Autowired
    private AuthenticationManager authManager; //impl AuthenticationMaganer no SecurityConfig

    @Autowired
    private TokenService tokenService; //Criar classe, pois há uma com mesmo nome

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginFormDto form){//Recebe um form de login e senha
        //Logica de autenticacao do JWT, precisa criar um metodo converter para pegar os dados que precisa
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();
        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            //Gerando o Token
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
