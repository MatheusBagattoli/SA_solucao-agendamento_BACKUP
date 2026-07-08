package com.senai.solucao_agendamento_SA.dtos.reserva;

import jakarta.validation.constraints.NotBlank;

public record CancelamentoReservaDto(

        @NotBlank
        String observacao
) {
}
