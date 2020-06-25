package com.projeto.forum.config.security;

import com.projeto.forum.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@EnableWebSecurity
@Configuration
//@RequiredArgsConstructor - não posso usar, precisa do Autowired
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override //impl com JWT
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }


    //Config autentica, controle de acesso - login
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //class que tem a logica de autenticacao do usuario
        //passwordEnconder - BCrypt.. cyrpt da senha
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Config autorização - URL/API, perfil de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception { //Toda URL nova URL precisa ter a permissão
        http.authorizeRequests()
        //Get
        //URL GET liberada para qualquer um idependente do metodo
        .antMatchers(HttpMethod.GET,"/topicos").permitAll()
        .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
        .antMatchers(HttpMethod.POST, "/auth").permitAll() //URL de login
        .anyRequest().authenticated() //qualquer outra request precisa estar autenticada - Add UserDetails na Classe que representa o Usuario/Perfil
        //.and().formLogin(); //gera o form de autenticação - agora o Login é usando o JWT, não tem mais o form de login do Spring
        .and().csrf().disable() //evitar ataques do tipo csrf
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //politica de criação de sessão (pom jjwt)
        .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);

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
