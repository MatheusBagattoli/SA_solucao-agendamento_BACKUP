package com.senai.solucao_agendamento_SA.services;

import com.senai.solucao_agendamento_SA.dtos.UsuarioAtualizarDto;
import com.senai.solucao_agendamento_SA.dtos.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.dtos.UsuarioLogin;
import com.senai.solucao_agendamento_SA.dtos.UsuarioSaidaDto;
import com.senai.solucao_agendamento_SA.entitys.UsuarioEntity;
import com.senai.solucao_agendamento_SA.mapper.UsuarioMapeamento;
import com.senai.solucao_agendamento_SA.repositorys.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapeamento usuarioMapeamento;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapeamento usuarioMapeamento) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapeamento = usuarioMapeamento;
    }



    //Logando usuario
    public void realizarLogin(UsuarioLogin login){

        UsuarioEntity usuarioEntity = usuarioRepository.findByEmailAndSenha(login.email(), login.senha())
                .orElseThrow(()-> new RuntimeException("Usuário ou senha incoreta!"));

        usuarioMapeamento.EntityparaDto(usuarioEntity);
    }


    //Cadastro usuario
    public void cadastroUsuario(UsuarioCadastroDto cadastroDto){

        if (usuarioRepository.existsByEmail(cadastroDto.email())){
            throw new IllegalArgumentException("Email ja existente");
        }
        if(usuarioRepository.existsByMatricula(cadastroDto.matricula())){
            throw new IllegalArgumentException("Matricula ja existente");
        }
        UsuarioEntity usuarioEntity = usuarioMapeamento.DtoparaEntity(cadastroDto);
        usuarioRepository.save(usuarioEntity);
    }


    //Listar todos usuarios
    public List<UsuarioSaidaDto> listaDeUsuarios(){

        List<UsuarioEntity> listaDeUsuarios = usuarioRepository.findAll();

        List<UsuarioSaidaDto> listaDeUsuariosDto = new ArrayList<>();

        for (UsuarioEntity usuarioEntity : listaDeUsuarios) {
            listaDeUsuariosDto.add(usuarioMapeamento.EntityparaDto(usuarioEntity));
        }
        return listaDeUsuariosDto;
    }


    //Obter usuario pela matricula
    public UsuarioAtualizarDto obterUsuarioParaAtualizar(String matricula){

        UsuarioEntity usuario = usuarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        return usuarioMapeamento.AtualizarUsuario(usuario);
    }


    //Atualizar Usuario
    public boolean atualizarUsuario(UsuarioAtualizarDto dto){

        UsuarioEntity usuario = usuarioRepository.findByMatricula(dto.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        usuario.setNome(dto.getEditarNome());
        usuario.setDataNascimento(dto.getEditarDataNascimento());
        usuario.setEmail(dto.getEditarEmail());
        usuario.setSenha(dto.getEditarSenha());

        usuarioRepository.save(usuario);

        return true;
    }


}
