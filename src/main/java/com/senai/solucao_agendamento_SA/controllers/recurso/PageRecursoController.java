package com.senai.solucao_agendamento_SA.controllers.recurso;

import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoAtualizar;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDto;
import com.senai.solucao_agendamento_SA.entities.DiasSemana;
import com.senai.solucao_agendamento_SA.services.RecursoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageRecursoController {

    private final RecursoService recursoServicee;

    public PageRecursoController(RecursoService recursoService) {
        this.recursoServicee = recursoService;
    }

    @GetMapping("/recursoCadastra")
    public String getCadastraEspacoequipamento(Model model){
        model.addAttribute("recurso", RecursoDto.parametrosVazios()); // chamei o metodo vazio da classe recor EspacoEquipamentoEntradaDto.
        model.addAttribute("diasSemana", DiasSemana.values()); //Gera as opções automaticamente pelo enum.
        return "recursoCadastra";
    }

    @GetMapping("/recursoLista")
    public String getListaEspacoEquipamento(Model model){
        List<RecursoDto> lista = recursoServicee.listarRecurso();
        model.addAttribute("recurso", lista);
        return "recursoLista";
    }


    @GetMapping("/recursoAtualizar/{id}")
    public String getRecursoAtualizar(@PathVariable @Valid Long id, Model model){
        RecursoAtualizar dto = recursoServicee.buscarRecursoPorId(id);

        model.addAttribute("recurso", dto);
        return "recursoAtualizar";
    }


}
