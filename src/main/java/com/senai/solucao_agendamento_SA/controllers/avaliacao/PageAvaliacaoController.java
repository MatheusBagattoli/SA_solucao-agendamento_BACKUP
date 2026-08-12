package com.senai.solucao_agendamento_SA.controllers.avaliacao;

import com.senai.solucao_agendamento_SA.dtos.avaliacao.AvaliacaoEntradaDto;
import com.senai.solucao_agendamento_SA.dtos.reserva.ReservaSaidaDto;
import com.senai.solucao_agendamento_SA.sessao.SessaoDto;
import com.senai.solucao_agendamento_SA.sessao.SessaoUtil;
import com.senai.solucao_agendamento_SA.services.AvaliacaoService;
import com.senai.solucao_agendamento_SA.services.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageAvaliacaoController {

    private final ReservaService reservaService;
    private final AvaliacaoService avaliacaoService;

    public PageAvaliacaoController(
            ReservaService reservaService,
            AvaliacaoService avaliacaoService
    ) {

        this.reservaService = reservaService;

        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping("/avaliacaoCadastrar/{reservaId}")
    public String novo(@PathVariable Long reservaId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        try {
            ReservaSaidaDto reserva =
                    reservaService.buscar(reservaId);
            if (!reserva.colaboradorId()
                    .equals(usuarioLogado.getId())) {
                throw new IllegalArgumentException("Você não pode avaliar uma reserva de outro usuário.");
            }

            if (!avaliacaoService.podeAvaliar(reservaId, usuarioLogado.getId())){
                throw new IllegalArgumentException("Esta reserva não pode ser avaliada.");
            }

            model.addAttribute("reserva", reserva);

            model.addAttribute("avaliacao", new AvaliacaoEntradaDto(null, ""));
            return "avaliacaoCadastrar";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/reservaLista";
        }
    }
}