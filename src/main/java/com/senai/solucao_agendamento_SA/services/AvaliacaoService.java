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

    // Verifica se a reserva pode receber uma avaliação
    public boolean podeAvaliar(Long reservaId) {

        ReservaEntity reserva = reservaRepository.findById(reservaId).orElse(null);

        if (reserva == null) {
            return false;
        }

        // Reserva cancelada não pode ser avaliada
        if (reserva.getDataCancelamento() != null) {
            return false;
        }

        // Impede avaliar duas vezes a mesma reserva
        if (avaliacaoRepository.existsByReserva_Id(reservaId)) {
            return false;
        }

        // A reserva precisa ter terminado
        LocalDateTime fimDaReserva = LocalDateTime.of(reserva.getData(), reserva.getHoraFinal());
        return LocalDateTime.now().isAfter(fimDaReserva);
    }

    // Salva a avaliação
    public void salvar(Long reservaId, AvaliacaoEntradaDto dto) {

        ReservaEntity reserva = reservaRepository.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva nao encontrada."));

        if (reserva.getDataCancelamento() != null) {
            throw new IllegalArgumentException("Nao e possivel avaliar uma reserva cancelada.");
        }

        if (avaliacaoRepository.existsByReserva_Id(reservaId)) {
            throw new IllegalArgumentException("Esta reserva ja foi avaliada.");
        }

        LocalDateTime fimDaReserva = LocalDateTime.of(reserva.getData(), reserva.getHoraFinal());

        if (LocalDateTime.now().isBefore(fimDaReserva)) {
            throw new IllegalArgumentException("A avaliacao so pode ser realizada apos o termino da reserva.");
        }

        AvaliacaoEntity avaliacao = new AvaliacaoEntity();

        avaliacao.setNota(dto.nota());
        avaliacao.setComentario(dto.comentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        avaliacao.setReserva(reserva);

        avaliacaoRepository.save(avaliacao);
    }

    // Verifica se a reserva já foi avaliada
    public boolean jaFoiAvaliada(Long reservaId) {
        return avaliacaoRepository.existsByReserva_Id(reservaId);
    }
}