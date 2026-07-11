package com.senai.solucao_agendamento_SA.entities;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "recurso")
public class RecursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "dia_semana", joinColumns = @JoinColumn(name = "recurso_id"))
    @Column(name = "dia_semana", nullable = false)
    private List<DiaSemana> diaSemana;

    @Column(nullable = false)
    private LocalDate dataInicioAgendamento;

    @Column(nullable = false)
    private LocalDate dataFimAgendamento;

    @Column(nullable = false)
    private LocalTime  horaInicioAgendamento;

    @Column(nullable = false)
    private LocalTime  horaFimAgendamento;


    public RecursoEntity() {
    }

    public RecursoEntity(Long id, String descricao, String tipo, List<DiaSemana> diasSemana, LocalDate dataInicioAgendamento, LocalDate dataFimAgendamento, LocalTime horaInicioAgendamento, LocalTime horaFimAgendamento) {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
        this.diaSemana = diasSemana;
        this.dataInicioAgendamento = dataInicioAgendamento;
        this.dataFimAgendamento = dataFimAgendamento;
        this.horaInicioAgendamento = horaInicioAgendamento;
        this.horaFimAgendamento = horaFimAgendamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<DiaSemana> getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(List<DiaSemana> diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalDate getDataInicioAgendamento() {
        return dataInicioAgendamento;
    }

    public void setDataInicioAgendamento(LocalDate dataInicioAgendamento) {
        this.dataInicioAgendamento = dataInicioAgendamento;
    }

    public LocalDate getDataFimAgendamento() {
        return dataFimAgendamento;
    }

    public void setDataFimAgendamento(LocalDate dataFimAgendamento) {
        this.dataFimAgendamento = dataFimAgendamento;
    }

    public LocalTime getHoraInicioAgendamento() {
        return horaInicioAgendamento;
    }

    public void setHoraInicioAgendamento(LocalTime horaInicioAgendamento) {
        this.horaInicioAgendamento = horaInicioAgendamento;
    }

    public LocalTime getHoraFimAgendamento() {
        return horaFimAgendamento;
    }

    public void setHoraFimAgendamento(LocalTime horaFimAgendamento) {
        this.horaFimAgendamento = horaFimAgendamento;
    }


}
