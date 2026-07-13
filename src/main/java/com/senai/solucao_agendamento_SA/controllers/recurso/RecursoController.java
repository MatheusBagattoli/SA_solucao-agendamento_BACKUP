package com.senai.solucao_agendamento_SA.controllers.recurso;

import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoAtualizar;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDto;
import com.senai.solucao_agendamento_SA.entities.DiaSemana;
import com.senai.solucao_agendamento_SA.services.RecursoService;
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
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    //Cadastro Recurso
    @PostMapping("/recursoCadastra")
    public String cadastraRecurso(@Valid @ModelAttribute("recurso") RecursoDto entradaDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("diaSemana", DiaSemana.values()); // Adicionei porque toda vez que acontecia um erro
            return "recursoCadastra";                                 //e retornava a pagina novamente nao aparecia os dias da semana para escolher novamente.
        }
        try {
            recursoService.cadastraRecurso(entradaDto);
            redirectAttributes.addFlashAttribute("mensagem", "Recurso cadastrado com sucesso!");
            return "redirect:/recursoCadastra";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("recurso", entradaDto); // Mnatem os dados digitados.
            return "redirect:/recursoCadastra";
        }
    }


    //Atualizar
    @PostMapping("/recursoAtualizar")
    public String atualizarRecurso(@Valid @ModelAttribute("recurso") RecursoAtualizar recursoDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "recursoAtualizar";
        }

        try {
            recursoService.atualizarRecurso(recursoDto);
            redirectAttributes.addFlashAttribute("mensagem", "Recurso atualizado com sucesso!");
            return "redirect:/recursoAtualizar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            model.addAttribute("diaSemana", DiaSemana.values());
            return "redirect:/recursoAtualizar";
        }
    }

    //Deletar recurso
    @DeleteMapping("/recursoExcluir/{id}")
    public ResponseEntity<String> excluirRecurso(@PathVariable Long id) {
        boolean excluido = recursoService.RecursoExcluir(id);
        if (!excluido) {
            return ResponseEntity.status(404).body("Recurso não encontrado (já pode ter sido excluído antes).");
        }
        return ResponseEntity.ok().body("Excluido");
    }
}


