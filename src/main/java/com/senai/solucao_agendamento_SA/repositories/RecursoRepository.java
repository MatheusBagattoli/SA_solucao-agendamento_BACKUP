package com.senai.solucao_agendamento_SA.repositories;

import com.senai.solucao_agendamento_SA.entities.RecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecursoRepository extends JpaRepository<RecursoEntity, Long> {

    boolean existsByDescricao(String descricao);

    Optional<RecursoEntity> findById(Long id);
}
