package com.senai.solucao_agendamento_SA.services;

import org.springframework.stereotype.Service;

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


}
