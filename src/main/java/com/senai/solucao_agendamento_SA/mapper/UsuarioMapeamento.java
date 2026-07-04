package com.senai.solucao_agendamento_SA.mapper;

import com.senai.solucao_agendamento_SA.dtos.UsuarioAtualizarDto;
import com.senai.solucao_agendamento_SA.dtos.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.dtos.UsuarioLogin;
import com.senai.solucao_agendamento_SA.dtos.UsuarioSaidaDto;
import com.senai.solucao_agendamento_SA.entitys.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapeamento {

    //No mapper manual mister, o ideal é usar o @Component, para poder injetar no service via construtor

    //Converte DTO para Entity
    public UsuarioEntity DtoparaEntity(UsuarioCadastroDto usuarioDto){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(usuarioDto.nome());
        usuario.setDataNascimento(usuarioDto.dataNascimento());
        usuario.setMatricula(usuarioDto.matricula());
        usuario.setEmail(usuarioDto.email());
        usuario.setSenha(usuarioDto.senha());
        return usuario;
    }

    //Converte Entity em Dto
    public UsuarioSaidaDto EntityparaDto(UsuarioEntity usuario){
        return new UsuarioSaidaDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getMatricula(),
                usuario.getEmail()
        );
    }



    //Login
    public UsuarioEntity DtoparaEntityLogin(UsuarioLogin usuario){
        UsuarioEntity usuarioLogin = new UsuarioEntity();
        usuarioLogin.setEmail(usuario.email());
        usuarioLogin.setSenha(usuario.senha());
        return usuarioLogin;
    }



    //Atualizando o Usuario
    public UsuarioAtualizarDto AtualizarUsuario(UsuarioEntity usuario){

        return new UsuarioAtualizarDto(
                usuario.getMatricula(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getSenha()
        );
    }



}
