package com.senai.solucao_agendamento_SA.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageUsuarioControlle {


    @GetMapping("/")
    public String getIndex(){
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }



}
