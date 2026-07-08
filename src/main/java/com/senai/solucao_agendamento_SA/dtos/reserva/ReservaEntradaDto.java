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
}
