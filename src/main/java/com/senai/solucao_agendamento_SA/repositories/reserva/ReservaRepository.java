package com.senai.solucao_agendamento_SA.repositories.reserva;

import com.senai.solucao_agendamento_SA.entities.reserva.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {


}
