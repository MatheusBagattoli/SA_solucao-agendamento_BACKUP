package com.senai.solucao_agendamento_SA.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum DiaSemana {

    SEGUNDA,
    TERCA,
    QUARTA,
    QUINTA,
    SEXTA,
    SABADO,
    DOMINGO;

    // Converte uma data (LocalDate) no dia da semana correspondente deste enum
    public static DiaSemana deLocalDate(LocalDate data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        return switch (diaDaSemana) {
            case MONDAY -> SEGUNDA;
            case TUESDAY -> TERCA;
            case WEDNESDAY -> QUARTA;
            case THURSDAY -> QUINTA;
            case FRIDAY -> SEXTA;
            case SATURDAY -> SABADO;
            case SUNDAY -> DOMINGO;
        };
    }
}
