package com.senai.solucao_agendamento_SA.dtos.espacoequipamento;

import com.senai.solucao_agendamento_SA.entities.espacoequipamento.DiasSemana;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtualizarRecurso(

        Long id,

        @NotBlank(message = "Descrição é obrigatória.")
        @Size(min = 3, max = 100, message = "A descrição deve conter entre 3 e 100 caracteres.")
        String descricao,

        @NotBlank(message = "Tipo do serviço é obrigatório.")
        @Size(min = 3, max = 50, message = "O tipo deve conter entre 3 e 50 caracteres.")
        String tipo,

        @NotNull(message = "Informe o dia da semana, do agendamento.")
        DiasSemana diasSemana,

        @NotNull(message = "Informe a data inicial, do agendamento.")
        @FutureOrPresent(message = "A data de início não pode ser anterior à data atual.")
        LocalDate dataInicioAgendamento,

        @NotNull(message = "Informe a data final, do agendamento.")
        @FutureOrPresent(message = "A data deve ser hoje ou uma data futura.")
        LocalDate dataFimAgendamento,

        @NotNull(message = "Informe a hora do inicio, do agendamento.")
        LocalTime horaInicioAgendamento,

        @NotNull(message = "Informe a hora final, do agendamento.")
        LocalTime  horaFimAgendamento
) {
}
