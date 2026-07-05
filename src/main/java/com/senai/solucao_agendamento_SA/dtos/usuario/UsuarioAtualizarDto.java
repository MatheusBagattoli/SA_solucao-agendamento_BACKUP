package com.senai.solucao_agendamento_SA.dtos.usuario;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UsuarioAtualizarDto {

    private String matricula;


    @NotBlank(message = "Nome é obrigatório.")
    @Pattern(regexp = "^[\\p{L} ]+$",message = "Nome deve conter apenas letras.")
    private String editarNome;

    @NotNull(message = "Data de nascimento é obrigatoria.")
    @Past(message = "A data de nascimento deve ser verdadeira.")
    private LocalDate editarDataNascimento;

    @NotBlank(message = "E-mail é obrigatorio.")
    @Email(message = "E-mail deve ser verdadeiro")
    @Size(max=255,message = "e-mail deve ter no maximo 255 caracteres.")
    private String editarEmail;

    @NotBlank(message = "Senha é obrigatoria")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,}$",message = "e-mail deve ter minimo 5 caracteres, umsa letra maiúscula e uma minuscula, um caracter expecial e um numero.")
    private String editarSenha;


    public UsuarioAtualizarDto() {
    }

    public UsuarioAtualizarDto(String matricula, String editarNome, LocalDate editarDataNascimento, String editarEmail, String editarSenha) {
        this.matricula = matricula;
        this.editarNome = editarNome;
        this.editarDataNascimento = editarDataNascimento;
        this.editarEmail = editarEmail;
        this.editarSenha = editarSenha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }


    public String getEditarNome() {
        return editarNome;
    }

    public void setEditarNome(String editarNome) {
        this.editarNome = editarNome;
    }

    public LocalDate getEditarDataNascimento() {
        return editarDataNascimento;
    }

    public void setEditarDataNascimento(LocalDate editarDataNascimento) {
        this.editarDataNascimento = editarDataNascimento;
    }

    public String getEditarEmail() {
        return editarEmail;
    }

    public void setEditarEmail(String editarEmail) {
        this.editarEmail = editarEmail;
    }

    public String getEditarSenha() {
        return editarSenha;
    }

    public void setEditarSenha(String editarSenha) {
        this.editarSenha = editarSenha;
    }
}
