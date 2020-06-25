package com.projeto.forum.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
@RestControllerAdvice
public class ControllerHandler {// Tratando Exception dos RestControllers

    @Autowired
    private MessageSource messageSource; //Trata as mensagens de acordo com o idioma

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorFormDto> handle(MethodArgumentNotValidException exception){ //tratando a mensagem de erro
        List<ErrorFormDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale()); // mensagem no idioma correto do usuario
                ErrorFormDto erro = new ErrorFormDto(e.getField(), mensagem);
                dto.add(erro);
        });
        return dto;
    }
}
