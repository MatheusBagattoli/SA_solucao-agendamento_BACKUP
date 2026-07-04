package com.senai.solucao_agendamento_SA.repositorys;

import com.senai.solucao_agendamento_SA.entitys.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmailAndSenha(String email, String senha);
    Optional<UsuarioEntity> findById(Long id);
    Optional<UsuarioEntity> findByMatricula(String matricula);
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);
}
