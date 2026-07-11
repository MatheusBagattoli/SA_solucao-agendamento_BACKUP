package com.senai.solucao_agendamento_SA.controllers.usuario;


import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioAtualizarDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioSaidaDto;
import com.senai.solucao_agendamento_SA.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageUsuarioController {

    private final UsuarioService usuarioService;

    public PageUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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

    @GetMapping("/usuarioCadastro")
    public String getUsuarioCadastro(Model model){
        model.addAttribute("usuario", UsuarioCadastroDto.parametrosVazio()); // aqui eu chamei o metodo parametrosVazio da classe UsuarioCadastroDto para nao ter que fazer manual.
        return "usuarioCadastro";
    }

    @GetMapping("/usuarioLista")
    public String getUsuarioLista(Model model){
        List<UsuarioSaidaDto> listaUsuarios = usuarioService.listaDeUsuarios();
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarioLista";
    }

    @GetMapping("/usuarioAtualizar/{matricula}")
    public String getUsuarioAtualizar(@PathVariable @Valid String matricula, Model model){

        UsuarioAtualizarDto dto = usuarioService.obterUsuarioParaAtualizar(matricula);

        model.addAttribute("usuario", dto);

        return "usuarioAtualizar";
    }



}
