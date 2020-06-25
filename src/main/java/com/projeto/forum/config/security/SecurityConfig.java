package com.projeto.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Config autentica, controle de acesso - login
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }

    //Config autorização - URL/API, perfil de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/topicos").permitAll() //URL GET liberada para qualquer um idependente do metodo
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll();
    }

    //Config recursos estáticos - requisições, JS CSS img e etc..
    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
