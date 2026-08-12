package com.senai.solucao_agendamento_SA.controllers.reserva;

import com.senai.solucao_agendamento_SA.dtos.reserva.CancelamentoReservaDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.senai.solucao_agendamento_SA.dtos.reserva.ReservaEntradaDto;
import com.senai.solucao_agendamento_SA.services.RecursoService;
import com.senai.solucao_agendamento_SA.services.ReservaService;
import com.senai.solucao_agendamento_SA.services.UsuarioService;
import com.senai.solucao_agendamento_SA.services.AvaliacaoService;
import com.senai.solucao_agendamento_SA.sessao.SessaoDto;
import com.senai.solucao_agendamento_SA.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final RecursoService recursoService;
    private final AvaliacaoService avaliacaoService;

    public PageReservaController(ReservaService reservaService, UsuarioService usuarioService, RecursoService recursoService, AvaliacaoService avaliacaoService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.recursoService = recursoService;
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping("/reservaCadastrar")
    public String novo(Model model) {
        model.addAttribute("reserva", ReservaEntradaDto.parametrosVazios());
        model.addAttribute("usuarios", usuarioService.listaDeUsuarios());
        model.addAttribute("recursos", recursoService.listarParaSelecao());
        return "reservaCadastrar";
    }

    @GetMapping("/reservaLista")
    public String listar(Model model) {
        model.addAttribute("reservas", reservaService.listar());
        return "reservaLista";
    }

    @GetMapping("/reservaVisualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model, HttpSession session){
        var reserva = reservaService.buscar(id);
        model.addAttribute("reserva", reserva);

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        boolean podeAvaliar = usuarioLogado != null && reserva.colaboradorId().equals(usuarioLogado.getId()) && avaliacaoService.podeAvaliar(id, usuarioLogado.getId());
        model.addAttribute("podeAvaliar", podeAvaliar);
        model.addAttribute("mediaAvaliacao", avaliacaoService.calcularMediaPorRecurso(reserva.recursoId()));
        return "reservaVisualizar";
    }

    @GetMapping("/reservaCancelar/{id}")
    public String telaCancelar(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", reservaService.buscar(id));
        model.addAttribute("cancelamento", new CancelamentoReservaDto(""));
        return "reservaCancelar";
    }
}


