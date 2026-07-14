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

@Controller
public class PageReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final RecursoService recursoService;

    public PageReservaController(ReservaService reservaService, UsuarioService usuarioService, RecursoService recursoService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.recursoService = recursoService;
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
    public String visualizar(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", reservaService.buscar(id));
        return "reservaVisualizar";
    }

    @GetMapping("/reservaCancelar/{id}")
    public String telaCancelar(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", reservaService.buscar(id));
        model.addAttribute("cancelamento", new CancelamentoReservaDto(""));
        return "reservaCancelar";
    }
}


