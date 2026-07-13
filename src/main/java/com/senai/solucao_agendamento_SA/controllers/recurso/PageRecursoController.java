package com.senai.solucao_agendamento_SA.controllers.recurso;

import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoAtualizar;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDisponibilidadeDto;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDto;
import com.senai.solucao_agendamento_SA.entities.DiaSemana;
import com.senai.solucao_agendamento_SA.services.RecursoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
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
        model.addAttribute("diaSemana", DiaSemana.values()); //Gera as opções automaticamente pelo enum.
        return "recursoCadastra";
    }

    @GetMapping("/recursoLista")
    public String getListaEspacoEquipamento(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFim,
            Model model){

        boolean verificandoDisponibilidade = data != null && horaInicio != null && horaFim != null;

        if (verificandoDisponibilidade) {
            List<RecursoDisponibilidadeDto> lista = recursoServicee.verificarDisponibilidade(data, horaInicio, horaFim);
            model.addAttribute("recursos", lista);
        } else {
            List<RecursoDto> lista = recursoServicee.listarRecurso();
            model.addAttribute("recursos", lista);
        }

        model.addAttribute("verificando", verificandoDisponibilidade);
        model.addAttribute("dataConsultada", data);
        model.addAttribute("horaInicioConsultada", horaInicio);
        model.addAttribute("horaFimConsultada", horaFim);

        return "recursoLista";
    }

    //Tela simples de listagem de Recursos (com editar/excluir), separada do recursoLista
    @GetMapping("/recursos")
    public String getRecursos(Model model){
        model.addAttribute("recursos", recursoServicee.listarComId());
        return "recursos";
    }

    @GetMapping("/recursoAtualizar/{id}")
    public String getRecursoAtualizar(@PathVariable @Valid Long id, Model model){
        RecursoAtualizar dto = recursoServicee.buscarRecursoPorId(id);

        model.addAttribute("recurso", dto);
        model.addAttribute("diaSemana", DiaSemana.values());
        return "recursoAtualizar";
    }


}