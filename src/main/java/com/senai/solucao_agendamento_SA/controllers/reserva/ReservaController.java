package com.senai.solucao_agendamento_SA.controllers.reserva;

import com.senai.solucao_agendamento_SA.services.RecursoService;
import com.senai.solucao_agendamento_SA.services.ReservaService;
import com.senai.solucao_agendamento_SA.services.UsuarioService;
import com.senai.solucao_agendamento_SA.dtos.reserva.CancelamentoReservaDto;
import com.senai.solucao_agendamento_SA.dtos.reserva.ReservaEntradaDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final RecursoService recursoService;

    public ReservaController(ReservaService reservaService, UsuarioService usuarioService, RecursoService recursoService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.recursoService = recursoService;
    }

    @PostMapping("/reservaCadastrar")
    public String salvar(@Valid @ModelAttribute("reserva") ReservaEntradaDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listaDeUsuarios());
            model.addAttribute("recursos", recursoService.listarParaSelecao());
            return "reservaCadastrar";
        }

        try {
            reservaService.salvar(dto);
            redirectAttributes.addFlashAttribute("mensagem", "Reserva realizada com sucesso!");
            return "redirect:/reservaCadastrar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("reserva", dto);
            return "redirect:/reservaCadastrar";
        }
    }

    @PostMapping("/reservaCancelar/{id}")
    public String cancelar(@PathVariable Long id, @Valid @ModelAttribute("cancelamento") CancelamentoReservaDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", "Informe o motivo do cancelamento.");
            return "redirect:/reservaCancelar/" + id;
        }

        try {
            reservaService.cancelar(id, dto);
            redirectAttributes.addFlashAttribute("mensagem", "Reserva cancelada com sucesso!");
            return "redirect:/reservaLista";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/reservaCancelar/" + id;
        }
    }

}
