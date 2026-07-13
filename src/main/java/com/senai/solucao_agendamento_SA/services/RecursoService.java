package com.senai.solucao_agendamento_SA.services;

import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoAtualizar;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDisponibilidadeDto;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoDto;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoSelecaoDto;
import com.senai.solucao_agendamento_SA.entities.DiaSemana;
import com.senai.solucao_agendamento_SA.entities.RecursoEntity;
import com.senai.solucao_agendamento_SA.dtos.recurso.RecursoListaDto;
import com.senai.solucao_agendamento_SA.mapper.RecursoMapper;
import com.senai.solucao_agendamento_SA.repositories.RecursoRepository;
import com.senai.solucao_agendamento_SA.repositories.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecursoService {

    private RecursoRepository recursoRepository;
    private final ReservaRepository reservaRepository;

    public RecursoService(RecursoRepository recursoRepository, ReservaRepository reservaRepository) {
        this.recursoRepository= recursoRepository;
        this.reservaRepository = reservaRepository;
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
        recurso1.setDiaSemana(recurso.diaSemana());
        recurso1.setDataInicioAgendamento(recurso.dataInicioAgendamento());
        recurso1.setDataFimAgendamento(recurso.dataFimAgendamento());
        recurso1.setHoraInicioAgendamento(recurso.horaInicioAgendamento());
        recurso1.setHoraFimAgendamento(recurso.horaFimAgendamento());
        return true;
    }

    //Listagem simplificada (id + descricao) usada para popular o select de recursos na tela de reserva
    public List<RecursoSelecaoDto> listarParaSelecao(){
        List<RecursoEntity> lista = recursoRepository.findAll();

        List<RecursoSelecaoDto> listaDto = new ArrayList<>();
        for (RecursoEntity entity : lista) {
            listaDto.add(new RecursoSelecaoDto(entity.getId(), entity.getDescricao()));
        }
        return listaDto;
    }

    //Listagem completa com id, usada na tela simples de Recursos (com editar/excluir)
    public List<RecursoListaDto> listarComId(){
        List<RecursoEntity> lista = recursoRepository.findAll();

        List<RecursoListaDto> listaDto = new ArrayList<>();
        for (RecursoEntity entity : lista) {
            listaDto.add(new RecursoListaDto(
                    entity.getId(),
                    entity.getDescricao(),
                    entity.getTipo(),
                    entity.getDiaSemana(),
                    entity.getDataInicioAgendamento(),
                    entity.getDataFimAgendamento(),
                    entity.getHoraInicioAgendamento(),
                    entity.getHoraFimAgendamento()
            ));
        }
        return listaDto;
    }


    //Excluir
    public boolean RecursoExcluir(Long id){
        if (!recursoRepository.existsById(id)) {
            return false;
        }
        recursoRepository.deleteById(id);
        return true;
    }

    //Indicador de disponibilidade: para uma data e horario escolhidos, verifica se cada recurso esta disponivel
    //considerando periodo de agendamento, horario de funcionamento, dia da semana e reservas ja existentes.
    public List<RecursoDisponibilidadeDto> verificarDisponibilidade(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        List<RecursoEntity> lista = recursoRepository.findAll();
        List<RecursoDisponibilidadeDto> resultado = new ArrayList<>();

        for (RecursoEntity recurso : lista) {
            boolean disponivel = true;
            String motivo = null;

            if (data.isBefore(recurso.getDataInicioAgendamento()) || data.isAfter(recurso.getDataFimAgendamento())) {
                disponivel = false;
                motivo = "Fora do periodo de agendamento deste recurso.";
            } else if (horaInicio.isBefore(recurso.getHoraInicioAgendamento()) || horaFim.isAfter(recurso.getHoraFimAgendamento())) {
                disponivel = false;
                motivo = "Fora do horario de funcionamento deste recurso.";
            } else if (!recurso.getDiaSemana().contains(DiaSemana.deLocalDate(data))) {
                disponivel = false;
                motivo = "Recurso nao funciona neste dia da semana.";
            } else {
                boolean existeConflito = reservaRepository
                        .findByRecurso_IdAndDataAndDataCancelamentoIsNull(recurso.getId(), data)
                        .stream()
                        .anyMatch(reservaExistente ->
                                horaInicio.isBefore(reservaExistente.getHoraFinal()) &&
                                        horaFim.isAfter(reservaExistente.getHoraInicial())
                        );
                if (existeConflito) {
                    disponivel = false;
                    motivo = "Ja existe uma reserva neste horario.";
                }
            }

            resultado.add(new RecursoDisponibilidadeDto(
                    recurso.getId(),
                    recurso.getDescricao(),
                    recurso.getTipo(),
                    recurso.getDiaSemana(),
                    recurso.getDataInicioAgendamento(),
                    recurso.getDataFimAgendamento(),
                    recurso.getHoraInicioAgendamento(),
                    recurso.getHoraFimAgendamento(),
                    disponivel,
                    motivo
            ));
        }
        return resultado;
    }

}