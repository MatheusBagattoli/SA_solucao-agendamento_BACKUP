package com.senai.solucao_agendamento_SA.services;

import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioAtualizarDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioCadastroDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioLoginDto;
import com.senai.solucao_agendamento_SA.dtos.usuario.UsuarioSaidaDto;
import com.senai.solucao_agendamento_SA.entities.UsuarioEntity;
import com.senai.solucao_agendamento_SA.mapper.UsuarioMapper;
import com.senai.solucao_agendamento_SA.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapeamento;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapeamento) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapeamento = usuarioMapeamento;
    }

    //Logando usuario
    public UsuarioEntity realizarLogin(UsuarioLoginDto login){

        return usuarioRepository.findByEmailAndSenha(login.email(), login.senha())
                .orElseThrow(()-> new RuntimeException("Usuário ou senha incoreta!"));
    }


    //Cadastro usuario
    public void cadastroUsuario(UsuarioCadastroDto cadastroDto){

        if (usuarioRepository.existsByEmail(cadastroDto.email())){
            throw new IllegalArgumentException("Email ja existente");
        }
        if(usuarioRepository.existsByMatricula(cadastroDto.matricula())){
            throw new IllegalArgumentException("Matricula ja existente");
        }
        if (!cadastroDto.senha().equals(cadastroDto.confirmaSenha())){
            throw new IllegalArgumentException("Senha incorreta");
        }

        LocalDate hoje = LocalDate.now(); // Obtém a data atual do sistema

        LocalDate dataMinima = hoje.minusYears(500); // Calcula a data mínima permitida (500 anos atrás)

        // Verifica se a data possui mais de 500 anos
        if (cadastroDto.dataNascimento().isBefore(dataMinima)) {
            throw new IllegalArgumentException("A data de nascimento não pode ter mais de 500 anos.");
        }

        UsuarioEntity usuarioEntity = usuarioMapeamento.dtoparaEntity(cadastroDto);
        usuarioRepository.save(usuarioEntity);
    }


    //Listar todos usuarios
    public List<UsuarioSaidaDto> listaDeUsuarios(){

        List<UsuarioEntity> listaDeUsuarios = usuarioRepository.findAll();

        List<UsuarioSaidaDto> listaDeUsuariosDto = new ArrayList<>();

        for (UsuarioEntity usuarioEntity : listaDeUsuarios) {
            listaDeUsuariosDto.add(usuarioMapeamento.entityparaDto(usuarioEntity));
        }
        return listaDeUsuariosDto;
    }


    //Obter usuario pela matricula
    public UsuarioAtualizarDto obterUsuarioParaAtualizar(String matricula){

        UsuarioEntity usuario = usuarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        return usuarioMapeamento.atualizarUsuario(usuario);
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


    //Excluir usuario
    public void excluirUsuario(Long id){
        usuarioRepository.deleteById(id);
    }


}
