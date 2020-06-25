package com.projeto.forum.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
    //Filtro para requisições que precisam do Token/Autorização
    //Precisa registrar o filtro para o Spring na classe SecurityConfig

    private TokenService tokenService; //Não se faz injeção na classe do filtro

    public AutenticacaoViaTokenFilter(TokenService tokenService){
        this.tokenService = tokenService;
    }

    //Como a nossa aplicação é stateless, "não existe mais usuário logado", api fica perguntando a cada requisição o token do usuário
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);
        System.out.println(valido);


        filterChain.doFilter(request, response); //Spring, essa é a resposta da requisição depois que rodar tudo
    }

    private String recuperarToken(HttpServletRequest request) {
        //Está no Header to Postman, junto com o Content-Type - application/json
        //Key: Authorization - Value: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZm8gRsOzcnVtIiwic3ViIjoiMSIsImlhdCI6MTU5MzEyMDU0MSwiZXhwIjoxNTkzMjA2OTQxfQ.WQrBmWRvL0HRnIV4rUWmo-XwgnOvgPadOefm3mgUvKo
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){ //precisa ter um espaço depois do Bearer
            return null;
        }
        return token.substring(7, token.length()); //7 porque é o tamanho do Bearer mais espaço após ele.
    }
}
