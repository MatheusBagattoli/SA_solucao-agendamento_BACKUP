package com.senai.solucao_agendamento_SA.dtos.recurso;

import com.senai.solucao_agendamento_SA.entities.DiasSemana;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record RecursoDto(


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

    //Este metodo pertence ao record, aqui estou criando EspacoEquipamento_EntradaDto vazio, para que eu não tenha que informa no controller estes parametros,
    //assim só pucho este metodo la no controller, evitando repetição
    public static RecursoDto parametrosVazios(){
        return new RecursoDto(
                "","",null,null,null,null,null
        );
    }

}
