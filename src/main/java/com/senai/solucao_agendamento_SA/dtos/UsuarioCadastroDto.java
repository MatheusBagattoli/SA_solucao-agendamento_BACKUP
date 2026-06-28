package com.senai.solucao_agendamento_SA.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioCadastroDto(

        // Garante que nome seja obrigatório e contenha apenas letras e espaços (sem números ou símbolos)
        @NotBlank
        @Pattern(regexp = "^[\\p{L} ]+$")
        String nome,

        @NotNull
        @Past
        LocalDate dataNascimento,

        @NotBlank
        @Email
        @Size(max=255)
        String email,

        //A senha deve ter no mínimo 8 caracteres, uma letra maiúscula, uma letra minúscula, um número e um caractere especia
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
        String senha

) {
}
