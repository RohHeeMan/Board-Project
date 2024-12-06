package com.codingrecipe.board.controller;

import ch.qos.logback.core.CoreConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        //org.springframework.boot:spring-boot-devtools 사용
        System.out.println("HomeController");
        return "index";
    }
}
