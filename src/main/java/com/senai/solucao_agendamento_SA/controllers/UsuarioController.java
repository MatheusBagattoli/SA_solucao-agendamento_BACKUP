package com.senai.solucao_agendamento_SA.controllers;

import com.senai.solucao_agendamento_SA.dtos.UsuarioLogin;
import com.senai.solucao_agendamento_SA.mapper.UsuarioMapeamento;
import com.senai.solucao_agendamento_SA.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapeamento usuarioMapeamento;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapeamento usuarioMapeamento) {
        this.usuarioService = usuarioService;
        this.usuarioMapeamento = usuarioMapeamento;
    }


    //Realizando login
    @PostMapping("/login")
    public String realizarLogin(UsuarioLogin login, Model model, RedirectAttributes redirectAttributes){
        try {
            usuarioMapeamento.DtoparaEntityLogin(login);
            usuarioService.realizarLogin(login); // puchou direto o metodo do service de realizarLogin.

            redirectAttributes.addFlashAttribute("usuarioLogin", login);
            return "redirect:/home";
        }catch (Exception e){
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }

}
