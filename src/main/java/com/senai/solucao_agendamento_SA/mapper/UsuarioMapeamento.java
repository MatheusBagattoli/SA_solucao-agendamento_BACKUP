package com.senai.solucao_agendamento_SA.mapper;

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
        usuario.setNome(usuario.getNome());
        usuario.setDataNascimento(usuario.getDataNascimento());
        usuario.setEmail(usuario.getEmail());
        usuario.setSenha(usuario.getSenha());
        return usuario;
    }

    //Converte Entity em Dto
    public UsuarioSaidaDto EntityparaDto(UsuarioEntity usuario){
        return new UsuarioSaidaDto(
                usuario.getNome(),
                usuario.getDataNascimento(),
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

}
