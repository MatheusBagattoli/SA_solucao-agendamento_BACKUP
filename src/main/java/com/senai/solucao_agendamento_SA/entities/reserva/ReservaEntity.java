package com.senai.solucao_agendamento_SA.entities.reserva;

import jakarta.persistence.*;

@Entity
@Table(name = "reserva")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
