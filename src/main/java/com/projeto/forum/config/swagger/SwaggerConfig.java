package com.projeto.forum.config.swagger;

import com.projeto.forum.modelo.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

/**
 * Created by Luis Gustavo Ullmann on 26/06/2020
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket forumApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis((RequestHandlerSelectors.basePackage("com.projeto.forum")))
                .paths(PathSelectors.ant("/**")) //Não temos nenhuma URL restrita, caso tenha precisa determinar
                .build()
                .ignoredParameterTypes(Usuario.class) //Ignore todas as URLs da classe Usuario nos endpoitns, para não expor a senha
                //Config parametro global de acesso com o token (delete, post e ect)
                .globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                        .name("Authorization")
                        .description("Header para token JWT")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()));

        //http://localhost:8080/swagger-ui.html
        //Precisa liberar o acesso ao Swagger na SecurityConfig
    }
}
