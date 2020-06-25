package com.projeto.forum.config.security;

import com.projeto.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@Service
public class TokenService {

    //application.properties - jwt.expiration=864000 tem que irá expirar = 1 dia
    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret; //senha

    public String gerarToken(Authentication authentication) {
       Usuario logado = (Usuario) authentication.getPrincipal(); //recupera o usuário logado
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API fo Fórum") //Quem está gerabdo o token
                .setSubject(logado.getId().toString()) //Usuario
                .setIssuedAt(hoje) //data de geração do token
                .setExpiration(dataExpiracao) //quando a sessão expira
                //1 param - quem é o algoritmo de cryptografia
                //2 param - senha da aplicação para gerar o hash
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact(); //compact transforma tudo numa String
    }

    public boolean isTokenValido(String token) {//Faz a validação do Token
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true; //valido
        } catch (Exception e){
            return false; //invalido
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody(); //devolve o corpo do token
        return Long.parseLong(claims.getSubject());
    }
}
