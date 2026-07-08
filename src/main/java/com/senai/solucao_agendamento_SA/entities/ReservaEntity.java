package com.senai.solucao_agendamento_SA.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name="recurso_id")
    private RecursoEntity recurso;

    private LocalDate data;

    private LocalTime horaInicial;

    private LocalTime horaFinal;

    private LocalDate dataCancelamento;

    @Column(length = 500)
    private String observacao;

}
