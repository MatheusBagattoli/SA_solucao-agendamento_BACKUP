package com.senai.solucao_agendamento_SA.services.espacoequipamento;

import com.senai.solucao_agendamento_SA.dtos.espacoequipamento.EspacoEquipamentoEntradaDto;
import com.senai.solucao_agendamento_SA.mapper.espacoequipamento.EspacoEquipamentoMapper;
import com.senai.solucao_agendamento_SA.repositories.espacoequipamento.EspacoEquipamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class EspacoEquipamentoService {

    private EspacoEquipamentoRepository espacoEquipamentoRepository;


    //Cadastrar
    public void cadastroEspacoEquipamento(EspacoEquipamentoEntradaDto entradaDto) {

        if (espacoEquipamentoRepository.existsByDescricao(entradaDto.descricao())){
            throw new IllegalArgumentException("Descrição ja existente");
        }

        // antes de 08:00  .getHour() ele serve para pegar só a hora e nao minutos.
        if (entradaDto.horaInicioAgendamento().getHour() < 8){
            throw new IllegalArgumentException("horario inicial apartir das 8:00 horas.");
        }

        // depois de 18:00
        if (entradaDto.horaFimAgendamento().getHour() > 18){
            throw new IllegalArgumentException("horario fim apartir das 18:00 horas.");
        }

        // isBefore() significa exemplo (a vem antes de b)
        if (!entradaDto.horaInicioAgendamento().isBefore(entradaDto.horaFimAgendamento())){
            throw new IllegalArgumentException("Horario invalido (08:00 as 18:00)");
        }


        espacoEquipamentoRepository.save(EspacoEquipamentoMapper.dtoParaEntity(entradaDto));
    }

    //Listar

    //Buscar por ID

    //Atualizar

    //Excluir

}
