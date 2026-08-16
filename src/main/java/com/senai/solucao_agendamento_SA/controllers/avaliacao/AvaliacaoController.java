package com.senai.solucao_agendamento_SA.controllers.avaliacao;

import com.senai.solucao_agendamento_SA.dtos.avaliacao.AvaliacaoEntradaDto;
import com.senai.solucao_agendamento_SA.services.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(
            AvaliacaoService avaliacaoService
    ) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping("/avaliacaoCadastrar/{id}")
    public String salvar(@PathVariable Long id, @Valid @ModelAttribute("avaliacao") AvaliacaoEntradaDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", "Informe uma nota entre 1 e 5.");

            return "redirect:/avaliacaoCadastrar/" + id;
        }

        try {
            avaliacaoService.salvar(id, dto);
            redirectAttributes.addFlashAttribute("mensagem", "Avaliacao realizada com sucesso!");

            return "redirect:/reservaVisualizar/" + id;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return "redirect:/reservaVisualizar/" + id;
        }
    }
}