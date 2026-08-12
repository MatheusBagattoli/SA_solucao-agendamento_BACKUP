package com.senai.solucao_agendamento_SA.services;

import com.senai.solucao_agendamento_SA.dtos.avaliacao.AvaliacaoEntradaDto;
import com.senai.solucao_agendamento_SA.entities.AvaliacaoEntity;
import com.senai.solucao_agendamento_SA.entities.ReservaEntity;
import com.senai.solucao_agendamento_SA.repositories.AvaliacaoRepository;
import com.senai.solucao_agendamento_SA.repositories.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ReservaRepository reservaRepository;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            ReservaRepository reservaRepository
    ) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.reservaRepository = reservaRepository;
    }

    public void salvar(Long reservaId, Long usuarioId, AvaliacaoEntradaDto dto) {

        ReservaEntity reserva = reservaRepository.findById(reservaId).orElseThrow(() ->
                new IllegalArgumentException("Reserva não encontrada."));

        validarReserva(reserva, usuarioId);

        AvaliacaoEntity avaliacao = new AvaliacaoEntity();
        avaliacao.setNota(dto.nota());

        avaliacao.setComentario(dto.comentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        avaliacao.setReserva(reserva);

        avaliacaoRepository.save(avaliacao);
    }

    public boolean podeAvaliar(Long reservaId, Long usuarioId){

        try {
            ReservaEntity reserva = reservaRepository.findById(reservaId).orElseThrow(() ->
                    new IllegalArgumentException("Reserva não encontrada."));
            validarReserva(reserva, usuarioId);
            return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Double calcularMediaPorRecurso(Long recursoId){

        Double media = avaliacaoRepository.calcularMediaPorRecurso(recursoId);
        return media == null ? 0.0 : media;
    }

    private void validarReserva(ReservaEntity reserva, Long usuarioId) {

        // Verifica se a reserva pertence ao usuário
        if (!reserva.getUsuario().getId().equals(usuarioId)){

            throw new IllegalArgumentException("Você não pode avaliar uma reserva de outro usuário.");
        }

        // Reserva cancelada não pode ser avaliada
        if (reserva.getDataCancelamento() != null){

            throw new IllegalArgumentException("Não é possível avaliar uma reserva cancelada.");
        }

        // Verifica se a reserva já terminou
        LocalDateTime fimDaReserva = reserva.getData().atTime(reserva.getHoraFinal());

        if (fimDaReserva.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("A avaliação só pode ser feita após o término da reserva.");
        }

        // Impede avaliação duplicada
        if (avaliacaoRepository.existsByReserva_Id(reserva.getId())){
            throw new IllegalArgumentException("Esta reserva já foi avaliada.");
        }
    }
}