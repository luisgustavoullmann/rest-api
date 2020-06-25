package com.projeto.forum.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@EnableWebSecurity
@Configuration
//@RequiredArgsConstructor - não posso usar, precisa do Autowired
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    //Config autentica, controle de acesso - login
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //class que tem a logica de autenticacao do usuario
        //passwordEnconder - BCrypt.. cyrpt da senha
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Config autorização - URL/API, perfil de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //Get
                //URL GET liberada para qualquer um idependente do metodo
                .antMatchers(HttpMethod.GET,"/topicos").permitAll()
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
                .anyRequest().authenticated() //qualquer outra request precisa estar autenticada - Add UserDetails na Classe que representa o Usuario/Perfil
                .and().formLogin(); //gera o form de autenticação
    }

    //Config recursos estáticos - requisições, JS CSS img e etc..
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    //Gerando o encode de uma senha para testar - data.sql
//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
//    }
}
