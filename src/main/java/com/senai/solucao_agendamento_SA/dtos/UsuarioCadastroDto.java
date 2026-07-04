package com.senai.solucao_agendamento_SA.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioCadastroDto(

        // Garante que nome seja obrigatório e contenha apenas letras e espaços (sem números ou símbolos)
        @NotBlank(message = "Nome é obrigatório.")
        @Pattern(regexp = "^[\\p{L} ]+$",message = "Nome deve conter apenas letras.")
        String nome,

        @NotNull(message = "Data de nascimento é obrigatoria.")
        @Past(message = "A data de nascimento deve ser verdadeira.")
        LocalDate dataNascimento,

        @NotBlank
        String matricula,

        @NotBlank(message = "E-mail é obrigatorio.")
        @Email(message = "E-mail deve ser verdadeiro")
        @Size(max=255,message = "e-mail deve ter no maximo 255 caracteres.")
        String email,

        //A senha deve ter no mínimo 5 caracteres, uma letra maiúscula, uma letra minúscula, um número e um caractere especia
        @NotBlank(message = "Senha é obrigatoria")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,}$",message = "e-mail deve ter minimo 5 caracteres, umsa letra maiúscula e uma minuscula, um caracter expecial e um numero.")
        String senha

) {

        //Este metodo pertence ao record, aqui estou criando UsuarioCadastroDto vazio, para que eu não tenha que informa no controller estes parametros,
        //assim só pucho este metodo la no controller
        public static UsuarioCadastroDto parametrosVazio() {
                return new UsuarioCadastroDto(
                        "", null, "", "", ""
                );
        }

}
