package com.senai.solucao_agendamento_SA.controllers.avaliacao;

import com.senai.solucao_agendamento_SA.dtos.avaliacao.AvaliacaoEntradaDto;
import com.senai.solucao_agendamento_SA.sessao.SessaoDto;
import com.senai.solucao_agendamento_SA.sessao.SessaoUtil;
import com.senai.solucao_agendamento_SA.services.AvaliacaoService;
import jakarta.servlet.http.HttpSession;
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

    public AvaliacaoController(AvaliacaoService avaliacaoService){
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping("/avaliacaoCadastrar/{reservaId}")
    public String salvar(@PathVariable Long reservaId,@Valid @ModelAttribute("avaliacao") AvaliacaoEntradaDto dto,
                         BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes){

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null){
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", "Informe uma nota entre 1 e 5.");
            return "redirect:/avaliacaoCadastrar/" + reservaId;
        }

        try {

            avaliacaoService.salvar(reservaId, usuarioLogado.getId(), dto);

            redirectAttributes.addFlashAttribute("mensagem", "Avaliação registrada com sucesso!");

            return "redirect:/reservaVisualizar/" + reservaId;

        } catch (IllegalArgumentException e){

            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return "redirect:/reservaVisualizar/" + reservaId;
        }
    }
}