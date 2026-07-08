package com.senai.solucao_agendamento_SA.services;

import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoAtualizar;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDto;
import com.senai.solucao_agendamento_SA.entities.RecursoEntity;
import com.senai.solucao_agendamento_SA.mapper.RecursoMapper;
import com.senai.solucao_agendamento_SA.repositories.RecursoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecursoService {

    private RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository= recursoRepository;
    }

    //Cadastrar
    public void cadastraRecurso(RecursoDto entradaDto) {

        if (recursoRepository.existsByDescricao(entradaDto.descricao())){
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

        recursoRepository.save(RecursoMapper.dtoParaEntity(entradaDto));
    }

    //Listar
    public List<RecursoDto> listarRecurso(){
        List<RecursoEntity> lista = recursoRepository.findAll();

        List<RecursoDto> listaDto = new ArrayList<>();

        for (RecursoEntity entity : lista) {
            listaDto.add(RecursoMapper.entityParaDto(entity));
        }
        return listaDto;
    }


    //Buscar por ID
    public RecursoAtualizar buscarRecursoPorId(Long id){
        RecursoEntity recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado."));

        return  RecursoMapper.atualizarRecurso(recurso);
    }


    //Atualizar
    public boolean atualizarRecurso(RecursoAtualizar recurso){

        RecursoEntity recurso1 = recursoRepository.findById(recurso.id())
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
        recursoRepository.deleteById(id);
    }

}
