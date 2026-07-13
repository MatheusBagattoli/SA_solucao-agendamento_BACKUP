package com.senai.solucao_agendamento_SA.dtos.recurso;

import com.senai.solucao_agendamento_SA.entities.DiaSemana;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RecursoListaDto(
        Long id,
        String descricao,
        String tipo,
        List<DiaSemana> diaSemana,
        LocalDate dataInicioAgendamento,
        LocalDate dataFimAgendamento,
        LocalTime horaInicioAgendamento,
        LocalTime horaFimAgendamento

) {
}
