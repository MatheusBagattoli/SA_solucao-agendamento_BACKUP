package com.senai.solucao_agendamento_SA.services;

import com.senai.solucao_agendamento_SA.dtos.reserva.CancelamentoReservaDto;
import com.senai.solucao_agendamento_SA.dtos.reserva.ReservaEntradaDto;
import com.senai.solucao_agendamento_SA.dtos.reserva.ReservaSaidaDto;
import com.senai.solucao_agendamento_SA.entities.DiaSemana;
import com.senai.solucao_agendamento_SA.entities.RecursoEntity;
import com.senai.solucao_agendamento_SA.entities.ReservaEntity;
import com.senai.solucao_agendamento_SA.entities.UsuarioEntity;
import com.senai.solucao_agendamento_SA.mapper.ReservaMapper;
import com.senai.solucao_agendamento_SA.repositories.RecursoRepository;
import com.senai.solucao_agendamento_SA.repositories.ReservaRepository;
import com.senai.solucao_agendamento_SA.repositories.UsuarioRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    //Salvar reserva

    //public ReservaEntity salvar(ReservaEntradaDto dto){
    //
    //    UsuarioEntity colaborador =
    //            usuarioRepository.findById(dto.colaboradorId())
    //            .orElseThrow(() ->
    //              new RuntimeException("Colaborador inexistente"));
    //
    //    EspacoEquipamentoEntity recurso =
    //            recursoRepository.findById(dto.recursoId())
    //            .orElseThrow(() ->
    //              new RuntimeException("Recurso inexistente"));
    //
    //    ReservaEntity reserva = new ReservaEntity();
    //
    //    reserva.setColaborador(colaborador);
    //    reserva.setRecurso(recurso);
    //    reserva.setData(dto.data());
    //    reserva.setHoraInicial(dto.horaInicial());
    //    reserva.setHoraFinal(dto.horaFinal());
    //
    //    reserva.setDataCancelamento(null);
    //
    //    return repository.save(reserva);
    //
    //}


    //Listar

    //public List<ReservaEntity> listar(){
    //
    //    return repository.findAll();
    //
    //}


    //Visualizar

    //public ReservaEntity buscar(Long id){
    //
    //    return repository.findById(id)
    //            .orElseThrow();
    //
    //}


    //Cancelar
    //
    //Esta é a parte mais importante.

    //public void cancelar(Long id,
    //                     CancelamentoReservaDto dto){
    //
    //    ReservaEntity reserva =
    //            repository.findById(id).orElseThrow();
    //
    //    if(reserva.getDataCancelamento()!=null){
    //
    //        throw new RuntimeException(
    //                "Reserva já cancelada");
    //
    //    }
    //
    //    LocalDate hoje = LocalDate.now();
    //
    //    if(hoje.isAfter(reserva.getData().minusDays(1))){
    //
    //        throw new RuntimeException(
    //                "Só é possível cancelar até um dia antes.");
    //
    //    }
    //
    //    reserva.setDataCancelamento(LocalDate.now());
    //
    //    reserva.setObservacao(dto.observacao());
    //
    //    repository.save(reserva);
    //
    //}
    //Essa regra atende exatamente ao requisito:
    //
    //só pode cancelar até um dia antes da reserva.


        private final ReservaRepository reservaRepository;
        private final UsuarioRepository usuarioRepository;
        private final RecursoRepository recursoRepository;

        public ReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, RecursoRepository recursoRepository) {
            this.reservaRepository = reservaRepository;
            this.usuarioRepository = usuarioRepository;
            this.recursoRepository = recursoRepository;
        }

        //Salvar reserva
        public void salvar(ReservaEntradaDto dto) {

            UsuarioEntity colaborador = usuarioRepository.findById(dto.colaboradorId())
                    .orElseThrow(() -> new IllegalArgumentException("Colaborador nao encontrado."));

            RecursoEntity recurso = recursoRepository.findById(dto.recursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Recurso nao encontrado."));

            if (!dto.horaInicial().isBefore(dto.horaFinal())) {
                throw new IllegalArgumentException("O horario inicial deve ser antes do horario final.");
            }

            //A reserva nao pode ser feita fora da janela de agendamento do recurso (datas)
            if (dto.data().isBefore(recurso.getDataInicioAgendamento()) || dto.data().isAfter(recurso.getDataFimAgendamento())) {
                throw new IllegalArgumentException("A data informada esta fora do periodo de agendamento deste recurso.");
            }

            //A reserva nao pode ser feita fora do horario de funcionamento do recurso
            if (dto.horaInicial().isBefore(recurso.getHoraInicioAgendamento()) || dto.horaFinal().isAfter(recurso.getHoraFimAgendamento())) {
                throw new IllegalArgumentException("O horario informado esta fora do horario disponivel deste recurso.");
            }

            //O dia da semana da data escolhida precisa estar entre os dias disponiveis do recurso
            DiaSemana diaEscolhido = converterParaDiaSemana(dto.data());
            if (!recurso.getDiaSemana().contains(diaEscolhido)) {
                throw new IllegalArgumentException("Este recurso nao esta disponivel no dia da semana escolhido.");
            }

            //Verifica o conflito de horario com outras reservas ativas do mesmo recurso na mesma data
            boolean existeConflito = reservaRepository
                    .findByRecurso_IdAndDataAndDataCancelamentoIsNull(recurso.getId(), dto.data())
                    .stream()
                    .anyMatch(reservaExistente ->
                            dto.horaInicial().isBefore(reservaExistente.getHoraFinal()) &&
                                    dto.horaFinal().isAfter(reservaExistente.getHoraInicial())
                    );

            if (existeConflito) {
                throw new IllegalArgumentException("Ja existe uma reserva para este recurso neste dia e horario.");
            }

            ReservaEntity reserva = new ReservaEntity();
            reserva.setUsuario(colaborador);
            reserva.setRecurso(recurso);
            reserva.setData(dto.data());
            reserva.setHoraInicial(dto.horaInicial());
            reserva.setHoraFinal(dto.horaFinal());
            reserva.setDataCancelamento(null);

            reservaRepository.save(reserva);
        }

        //Listar todas as reservas
        public List<ReservaSaidaDto> listar() {
            List<ReservaEntity> lista = reservaRepository.findAll();

            List<ReservaSaidaDto> listaDto = new ArrayList<>();
            for (ReservaEntity entity : lista) {
                listaDto.add(ReservaMapper.entityParaSaidaDto(entity));
            }
            return listaDto;
        }

        //Buscar uma reserva pelo id (visualizacao e tela de cancelamento)
        public ReservaSaidaDto buscar(Long id) {
            ReservaEntity reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reserva nao encontrada."));

            return ReservaMapper.entityParaSaidaDto(reserva);
        }

        //Cancelar reserva
        public void cancelar(Long id, CancelamentoReservaDto dto) {

            ReservaEntity reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reserva nao encontrada."));

            if (reserva.getDataCancelamento() != null) {
                throw new IllegalArgumentException("Esta reserva ja foi cancelada.");
            }

            LocalDate hoje = LocalDate.now();
            LocalDate limiteParaCancelar = reserva.getData().minusDays(1);

            if (hoje.isAfter(limiteParaCancelar)) {
                throw new IllegalArgumentException("So e possivel cancelar ate 1 dia antes da data agendada.");
            }

            reserva.setDataCancelamento(hoje);
            reserva.setObservacao(dto.observacao());

            reservaRepository.save(reserva);
        }

        //Converte o dia da semana de um LocalDate para o enum DiaSemana usado no cadastro do recurso
        private DiaSemana converterParaDiaSemana(@NotNull LocalDate data) {
            DayOfWeek diaDaSemana = data.getDayOfWeek();
            return switch (diaDaSemana) {
                case MONDAY -> DiaSemana.SEGUNDA;
                case TUESDAY -> DiaSemana.TERCA;
                case WEDNESDAY -> DiaSemana.QUARTA;
                case THURSDAY -> DiaSemana.QUINTA;
                case FRIDAY -> DiaSemana.SEXTA;
                case SATURDAY -> DiaSemana.SABADO;
                case SUNDAY -> DiaSemana.DOMINGO;
            };
        }
    }


