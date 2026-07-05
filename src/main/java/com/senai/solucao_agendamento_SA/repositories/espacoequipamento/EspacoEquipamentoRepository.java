package com.senai.solucao_agendamento_SA.repositories.espacoequipamento;

import com.senai.solucao_agendamento_SA.entities.espacoequipamento.EspacoEquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspacoEquipamentoRepository extends JpaRepository<EspacoEquipamentoEntity, Long> {


}
