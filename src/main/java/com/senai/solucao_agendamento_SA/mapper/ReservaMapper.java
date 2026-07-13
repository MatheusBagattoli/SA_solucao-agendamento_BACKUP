package com.senai.solucao_agendamento_SA.mapper;

import com.senai.solucao_agendamento_SA.dtos.reserva.ReservaSaidaDto;
import com.senai.solucao_agendamento_SA.entities.ReservaEntity;

public class ReservaMapper {

    //Converte Entity em Dto de saida, ja trazendo o nome do usuario e a descricao do recurso
    public static ReservaSaidaDto entityParaSaidaDto(ReservaEntity entity) {
        return new ReservaSaidaDto(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getUsuario().getNome(),
                entity.getRecurso().getId(),
                entity.getRecurso().getDescricao(),
                entity.getData(),
                entity.getHoraInicial(),
                entity.getHoraFinal(),
                entity.getDataCancelamento(),
                entity.getObservacao()
        );
    }
}
