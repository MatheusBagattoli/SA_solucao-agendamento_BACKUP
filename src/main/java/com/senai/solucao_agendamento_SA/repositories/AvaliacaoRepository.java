package com.senai.solucao_agendamento_SA.repositories;

import com.senai.solucao_agendamento_SA.entities.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long> {

    boolean existsByReserva_Id(Long reservaId);
}
