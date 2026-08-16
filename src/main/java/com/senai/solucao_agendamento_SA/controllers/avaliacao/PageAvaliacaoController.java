package com.senai.solucao_agendamento_SA.controllers.avaliacao;

import com.senai.solucao_agendamento_SA.dtos.avaliacao.AvaliacaoEntradaDto;
import com.senai.solucao_agendamento_SA.services.AvaliacaoService;
import com.senai.solucao_agendamento_SA.services.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageAvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final ReservaService reservaService;


    public PageAvaliacaoController(AvaliacaoService avaliacaoService, ReservaService reservaService) {
        this.avaliacaoService = avaliacaoService;
        this.reservaService = reservaService;
    }

    @GetMapping("/avaliacaoCadastrar/{id}")
    public String cadastrar(@PathVariable Long id, Model model) {

        if (!avaliacaoService.podeAvaliar(id)) {
            return "redirect:/reservaVisualizar/" + id;
        }

        model.addAttribute("reserva", reservaService.buscar(id));
        model.addAttribute("avaliacao", AvaliacaoEntradaDto.parametrosVazios());
        return "avaliacaoCadastrar";
    }
}