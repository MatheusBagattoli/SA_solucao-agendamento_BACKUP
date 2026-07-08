package com.senai.solucao_agendamento_SA.repositories;

import com.senai.solucao_agendamento_SA.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {


}
