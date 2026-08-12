package com.senai.solucao_agendamento_SA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoRepository, Long>{

    boolean existsByReserva_Id(Long reservaId);

    @Query("""
        SELECT AVG(a.nota)
        FROM AvaliacaoEntity a
        WHERE a.reserva.recurso.id = :recursoId
    """)
    Double calcularMediaPorRecurso(
            @Param("recursoId") Long recursoId
    );

}
