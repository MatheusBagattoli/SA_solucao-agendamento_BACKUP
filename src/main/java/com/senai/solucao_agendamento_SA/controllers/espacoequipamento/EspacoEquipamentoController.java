package com.senai.solucao_agendamento_SA.controllers.espacoequipamento;

import com.senai.solucao_agendamento_SA.dtos.espacoequipamento.EspacoEquipamentoEntradaDto;
import com.senai.solucao_agendamento_SA.entities.espacoequipamento.DiasSemana;
import com.senai.solucao_agendamento_SA.services.espacoequipamento.EspacoEquipamentoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EspacoEquipamentoController {

    private final EspacoEquipamentoService espacoEquipamentoService;

    public EspacoEquipamentoController(EspacoEquipamentoService espacoEquipamentoService) {
        this.espacoEquipamentoService = espacoEquipamentoService;
    }

    //Cadastro Espaço/Equipamento
    @PostMapping("/cadastrarEspacoEquipamento")
    public String cadastrarEspacoEquipamento(@Valid @ModelAttribute("espacoequipamento")EspacoEquipamentoEntradaDto entradaDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("diasSemana", DiasSemana.values()); // Adicionei porque toda vez que acontecia um erro
            return "cadastraEspacoEquipamento";                                 //e retornava a pagina novamente nao aparecia os dias da semana para escolher novamente.
        }
        try {
            espacoEquipamentoService.cadastroEspacoEquipamento(entradaDto);
            redirectAttributes.addFlashAttribute("mensagem", "Espaco/Equipamento cadastrado com sucesso!");
            return "redirect:/cadastraEspacoEquipamento";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("espacoequipamento", entradaDto); // Mnatem os dados digitados.
            return "redirect:/cadastraEspacoEquipamento";
        }
    }

}
