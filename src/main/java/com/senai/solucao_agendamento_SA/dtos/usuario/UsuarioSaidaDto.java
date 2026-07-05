package com.senai.solucao_agendamento_SA.dtos.usuario;

import java.time.LocalDate;

public record UsuarioSaidaDto(

        Long id,
        String nome,
        LocalDate dataNascimento,
        String matricula,
        String email
) {
}
