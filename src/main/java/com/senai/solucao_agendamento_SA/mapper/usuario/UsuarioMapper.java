package com.senai.solucao_agendamento_SA.mapper.usuario;

import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioAtualizarDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioLoginDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioSaidaDto;
import com.senai.solucao_agendamento_SA.entities.usuario.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    //No mapper manual mister, o ideal é usar o @Component, para poder injetar no service via construtor

    //Converte DTO para Entity
    public UsuarioEntity dtoparaEntity(UsuarioCadastroDto usuarioDto){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(usuarioDto.nome());
        usuario.setDataNascimento(usuarioDto.dataNascimento());
        usuario.setMatricula(usuarioDto.matricula());
        usuario.setEmail(usuarioDto.email());
        usuario.setSenha(usuarioDto.senha());
        return usuario;
    }

    //Converte Entity em Dto
    public UsuarioSaidaDto entityparaDto(UsuarioEntity usuario){
        return new UsuarioSaidaDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getMatricula(),
                usuario.getEmail()
        );
    }



    //Login
    public UsuarioEntity dtoparaEntityLogin(UsuarioLoginDto usuario){
        UsuarioEntity usuarioLogin = new UsuarioEntity();
        usuarioLogin.setEmail(usuario.email());
        usuarioLogin.setSenha(usuario.senha());
        return usuarioLogin;
    }



    //Atualizando o Usuario
    public UsuarioAtualizarDto atualizarUsuario(UsuarioEntity usuario){

        return new UsuarioAtualizarDto(
                usuario.getMatricula(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getSenha()
        );
    }



}
