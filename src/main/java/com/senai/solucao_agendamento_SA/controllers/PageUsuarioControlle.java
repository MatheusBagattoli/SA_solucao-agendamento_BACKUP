package com.senai.solucao_agendamento_SA.controllers;


import com.senai.solucao_agendamento_SA.dtos.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.mapper.UsuarioMapeamento;
import com.senai.solucao_agendamento_SA.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageUsuarioControlle {

    private final UsuarioMapeamento usuarioMapeamento;

    public PageUsuarioControlle(UsuarioService usuarioService, UsuarioMapeamento usuarioMapeamento) {
        this.usuarioMapeamento = usuarioMapeamento;
    }

    @GetMapping("/")
    public String getIndex(){
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }


    @GetMapping("/cadastrarUsuario")
    public String getCadastrarUsuario(Model model){
        model.addAttribute("usuario", UsuarioCadastroDto.parametrosVazio()); // aqui eu chamei o metodo parametrosVazio da classe UsuarioCadastroDto para nao ter que fazer manual.
        return "cadastrarUsuario";

    }

}
