package com.projeto.forum.controller.dto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String getHello(){
        return "Hello World";
    }
}
