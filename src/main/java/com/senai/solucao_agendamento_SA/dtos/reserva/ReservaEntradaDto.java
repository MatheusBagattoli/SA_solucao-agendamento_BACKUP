package com.senai.solucao_agendamento_SA.dtos.reserva;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaEntradaDto(

        @NotNull
        Long colaboradorId,

        @NotNull
        Long recursoId,

        @NotNull
        LocalDate data,

        @NotNull
        LocalTime horaInicial,

        @NotNull
        LocalTime horaFinal

) {
        //Metodo de conveniencia para nao precisar informar os parametros manualmente no controller
        public static ReservaEntradaDto parametrosVazios() {
                return new ReservaEntradaDto(null, null, null, null, null);
        }
}

