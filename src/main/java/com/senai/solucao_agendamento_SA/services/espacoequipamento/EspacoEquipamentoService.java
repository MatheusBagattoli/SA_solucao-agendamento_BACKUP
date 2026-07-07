package com.senai.solucao_agendamento_SA.services.espacoequipamento;

import com.senai.solucao_agendamento_SA.dtos.espacoequipamento.AtualizarRecurso;
import com.senai.solucao_agendamento_SA.dtos.espacoequipamento.EspacoEquipamentoEntradaDto;
import com.senai.solucao_agendamento_SA.entities.espacoequipamento.EspacoEquipamentoEntity;
import com.senai.solucao_agendamento_SA.mapper.espacoequipamento.EspacoEquipamentoMapper;
import com.senai.solucao_agendamento_SA.repositories.espacoequipamento.EspacoEquipamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EspacoEquipamentoService {

    private EspacoEquipamentoRepository espacoEquipamentoRepository;

    public EspacoEquipamentoService(EspacoEquipamentoRepository espacoEquipamentoRepository) {
        this.espacoEquipamentoRepository = espacoEquipamentoRepository;
    }

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
    public List<EspacoEquipamentoEntradaDto> listarEspacoEquipamentos(){
        List<EspacoEquipamentoEntity> lista = espacoEquipamentoRepository.findAll();

        List<EspacoEquipamentoEntradaDto> listaDto = new ArrayList<>();

        for (EspacoEquipamentoEntity entity : lista) {
            listaDto.add(EspacoEquipamentoMapper.entityParaDto(entity));
        }
        return listaDto;
    }


    //Buscar por ID
    public AtualizarRecurso buscarRecursoPorId(Long id){
        EspacoEquipamentoEntity recurso = espacoEquipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado."));

        return  EspacoEquipamentoMapper.atualizarRecurso(recurso);
    }


    //Atualizar
    public boolean atualizarRecurso(AtualizarRecurso recurso){

        EspacoEquipamentoEntity recurso1 = espacoEquipamentoRepository.findById(recurso.id())
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        recurso1.setDescricao(recurso.descricao());
        recurso1.setTipo(recurso.tipo());
        recurso1.setDiasSemana(recurso.diasSemana());
        recurso1.setDataInicioAgendamento(recurso.dataInicioAgendamento());
        recurso1.setDataFimAgendamento(recurso.dataFimAgendamento());
        recurso1.setHoraInicioAgendamento(recurso.horaInicioAgendamento());
        recurso1.setHoraFimAgendamento(recurso.horaFimAgendamento());
        return true;
    }


    //Excluir
    public void excluirRecurso(Long id){
        espacoEquipamentoRepository.deleteById(id);
    }

}
