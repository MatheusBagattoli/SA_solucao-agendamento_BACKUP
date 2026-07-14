package com.senai.solucao_agendamento_SA.repositories;

import com.senai.solucao_agendamento_SA.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    //Busca as reservas ativas (nao canceladas) de um recurso em uma data, para checar conflito de horario
    List<ReservaEntity> findByRecurso_IdAndDataAndDataCancelamentoIsNull(Long recursoId, LocalDate data);

}
