package com.senai.solucao_agendamento_SA.controllers.espacoequipamento;

import com.senai.solucao_agendamento_SA.dtos.espacoequipamento.EspacoEquipamentoEntradaDto;
import com.senai.solucao_agendamento_SA.entities.espacoequipamento.DiasSemana;
import com.senai.solucao_agendamento_SA.services.espacoequipamento.EspacoEquipamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageEspacoEquipamentoController {

    private final EspacoEquipamentoService espacoEquipamentoService;

    public PageEspacoEquipamentoController(EspacoEquipamentoService espacoEquipamentoService) {
        this.espacoEquipamentoService = espacoEquipamentoService;
    }

    @GetMapping("/cadastraEspacoEquipamento")
    public String getCadastraEspacoequipamento(Model model){
        model.addAttribute("espacoequipamento", EspacoEquipamentoEntradaDto.parametrosVazios()); // chamei o metodo vazio da classe recor EspacoEquipamentoEntradaDto.
        model.addAttribute("diasSemana", DiasSemana.values()); //Gera as opções automaticamente pelo enum.
        return "cadastraEspacoEquipamento";
    }

    @GetMapping("/listarEspacoequipamento")
    public String getListaEspacoEquipamento(Model model){
        List<EspacoEquipamentoEntradaDto> lista = espacoEquipamentoService.listarEspacoEquipamentos();
        model.addAttribute("espacoequipamento", lista);
        return "listarEspacoequipamento";
    }

}
