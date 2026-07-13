package com.senai.solucao_agendamento_SA.dtos.reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaSaidaDto (

    Long id,

    Long colaboradorId,
    String colaboradorNome,

    Long recursoId,
    String recursoDescricao,

    LocalDate data,
    LocalTime horaInicial,
    LocalTime horaFinal,

    LocalDate dataCancelamento,
    String observacao

){
    //Facilita nas telas (th:if) saber se a reserva ja foi cancelada ou nao
    public boolean isCancelada() {
        return dataCancelamento != null;
    }
}
