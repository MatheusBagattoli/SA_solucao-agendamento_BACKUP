package com.senai.solucao_agendamento_SA.controllers.usuario;

import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioAtualizarDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioLoginDto;
import com.senai.solucao_agendamento_SA.mapper.UsuarioMapper;
import com.senai.solucao_agendamento_SA.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapeamento;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapeamento) {
        this.usuarioService = usuarioService;
        this.usuarioMapeamento = usuarioMapeamento;
    }


    //Realizando login
    @PostMapping("/login")
    public String realizarLogin(UsuarioLoginDto login, Model model, RedirectAttributes redirectAttributes){
        try {
            usuarioMapeamento.dtoparaEntityLogin(login);
            usuarioService.realizarLogin(login); // puchou direto o metodo do service de realizarLogin.

            redirectAttributes.addFlashAttribute("usuarioLogin", login);
            return "redirect:/home";
        }catch (Exception e){
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }


    //Cadastro usuario
    @PostMapping("/cadastrarUsuario")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuario") UsuarioCadastroDto cadastroDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "cadastrarUsuario";
        }

        try {
            usuarioService.cadastroUsuario(cadastroDto);
            redirectAttributes.addFlashAttribute("mensagem", "Usuario cadastrado com sucesso!");
            return"redirect:/home";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("usuario",cadastroDto); // Mantem os dados digitados
            return "redirect:/cadastrarUsuario";
        }
    }

    //Atualizar Usuario
    @PostMapping("/atualizarUsuario")
    public String atualizarUsuario(@Valid @ModelAttribute("usuario") UsuarioAtualizarDto dto, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return "atualizarUsuario";
        }

        try {
            usuarioService.atualizarUsuario(dto);

            redirectAttributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
            return "redirect:/listaUsuarios";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/atualizarUsuario/" + dto.getMatricula();
        }
    }


    //Deletar usuario
    @DeleteMapping("/usuarioExcluir/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes){
        usuarioService.excluirUsuario(id);
        return  ResponseEntity.ok().body("Excluido");
    }





}
