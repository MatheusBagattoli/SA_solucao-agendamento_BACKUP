package com.senai.solucao_agendamento_SA.mapper;

import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoAtualizar;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDto;
import com.senai.solucao_agendamento_SA.entities.RecursoEntity;


public class RecursoMapper {

    //Converte DTO para Entity
    public static RecursoEntity dtoParaEntity (RecursoDto entradaDto){
        RecursoEntity entity = new RecursoEntity();
        entity.setDescricao(entradaDto.descricao());
        entity.setTipo(entradaDto.tipo());
        entity.setDiaSemana(entradaDto.diaSemana());
        entity.setDataInicioAgendamento(entradaDto.dataInicioAgendamento());
        entity.setDataFimAgendamento(entradaDto.dataFimAgendamento());
        entity.setHoraInicioAgendamento(entradaDto.horaInicioAgendamento());
        entity.setHoraFimAgendamento(entradaDto.horaFimAgendamento());
        return entity;
    }


    //Converte Entity em Dto
    public static RecursoDto entityParaDto(RecursoEntity entity){
        return new RecursoDto(
                entity.getDescricao(),
                entity.getTipo(),
                entity.getDiaSemana(),
                entity.getDataInicioAgendamento(),
                entity.getDataFimAgendamento(),
                entity.getHoraInicioAgendamento(),
                entity.getHoraFimAgendamento()
        );
    }


    //Atualizando o Recurso
    public static RecursoAtualizar atualizarRecurso(RecursoEntity entity){
        return new RecursoAtualizar(
                entity.getId(),
                entity.getDescricao(),
                entity.getTipo(),
                entity.getDiaSemana(),
                entity.getDataInicioAgendamento(),
                entity.getDataFimAgendamento(),
                entity.getHoraInicioAgendamento(),
                entity.getHoraFimAgendamento()
        );
    }


}
