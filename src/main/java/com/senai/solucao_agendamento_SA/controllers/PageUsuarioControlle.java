package com.senai.solucao_agendamento_SA.controllers;


import com.senai.solucao_agendamento_SA.dtos.UsuarioAtualizarDto;
import com.senai.solucao_agendamento_SA.dtos.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.dtos.UsuarioSaidaDto;
import com.senai.solucao_agendamento_SA.mapper.UsuarioMapeamento;
import com.senai.solucao_agendamento_SA.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageUsuarioControlle {

    private UsuarioService usuarioService;
    private UsuarioMapeamento usuarioMapeamento;

    public PageUsuarioControlle(UsuarioService usuarioService, UsuarioMapeamento usuarioMapeamento) {
        this.usuarioService = usuarioService;
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

    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @GetMapping("/cadastrarUsuario")
    public String getCadastrarUsuario(Model model){
        model.addAttribute("usuario", UsuarioCadastroDto.parametrosVazio()); // aqui eu chamei o metodo parametrosVazio da classe UsuarioCadastroDto para nao ter que fazer manual.
        return "cadastrarUsuario";
    }

    @GetMapping("/listaUsuarios")
    public String getListaUsuarios(Model model){
        List<UsuarioSaidaDto> listaUsuarios = usuarioService.listaDeUsuarios();
        model.addAttribute("usuarios", listaUsuarios);
        return "listaUsuarios";
    }

    @GetMapping("/atualizarUsuario/{matricula}")
    public String getAtualizarUsuario(@PathVariable String matricula, Model model){

        UsuarioAtualizarDto dto = usuarioService.obterUsuarioParaAtualizar(matricula);

        model.addAttribute("usuario", dto);

        return "atualizarUsuario";
    }



}
