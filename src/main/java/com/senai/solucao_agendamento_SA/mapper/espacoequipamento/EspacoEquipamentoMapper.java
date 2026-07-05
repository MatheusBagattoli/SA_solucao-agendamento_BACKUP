package com.senai.solucao_agendamento_SA.mapper.espacoequipamento;

import com.senai.solucao_agendamento_SA.dtos.espacoequipamento.EspacoEquipamentoEntradaDto;
import com.senai.solucao_agendamento_SA.entities.espacoequipamento.EspacoEquipamentoEntity;


public class EspacoEquipamentoMapper {

    //Converte DTO para Entity
    public static EspacoEquipamentoEntity dtoParaEntity (EspacoEquipamentoEntradaDto entradaDto){
        EspacoEquipamentoEntity entity = new EspacoEquipamentoEntity();
        entity.setDescricao(entradaDto.descricao());
        entity.setTipo(entradaDto.tipo());
        entity.setDiasSemana(entradaDto.diasSemana());
        entity.setDataInicioAgendamento(entradaDto.dataInicioAgendamento());
        entity.setDataFimAgendamento(entradaDto.dataFimAgendamento());
        entity.setHoraInicioAgendamento(entradaDto.horaInicioAgendamento());
        entity.setHoraFimAgendamento(entradaDto.horaFimAgendamento());
        return entity;
    }


    //Converte Entity em Dto
    public static EspacoEquipamentoEntradaDto entityParaDto(EspacoEquipamentoEntity entity){
        return new EspacoEquipamentoEntradaDto(
                entity.getDescricao(),
                entity.getTipo(),
                entity.getDiasSemana(),
                entity.getDataInicioAgendamento(),
                entity.getDataFimAgendamento(),
                entity.getHoraInicioAgendamento(),
                entity.getHoraFimAgendamento()
        );
    }


}
