package com.senai.solucao_agendamento_SA.dtos;

import java.time.LocalDate;

public record UsuarioSaidaDto(

        Long id,
        String nome,
        LocalDate dataNascimento,
        String matricula,
        String email
) {
}
