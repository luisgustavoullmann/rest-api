package com.projeto.forum.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Luis Gustavo Ullmann on 25/06/2020
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String getHello(){
        return "Hello World";
    }
}
