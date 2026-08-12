package com.senai.solucao_agendamento_SA.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao")
public class AvaliacaoEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private Integer nota;

        @Column(length = 500)
        private String comentario;

        @Column(nullable = false)
        private LocalDateTime dataAvaliacao;

        @OneToOne
        @JoinColumn(
                name = "reserva_id",
                nullable = false,
                unique = true
        )
        private ReservaEntity reserva;

        public AvaliacaoEntity() {
        }

        public Long getId() {
            return id;
        }

        public Integer getNota() {
            return nota;
        }

        public void setNota(Integer nota) {
            this.nota = nota;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public LocalDateTime getDataAvaliacao() {
            return dataAvaliacao;
        }

        public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
            this.dataAvaliacao = dataAvaliacao;
        }

        public ReservaEntity getReserva() {
            return reserva;
        }

        public void setReserva(ReservaEntity reserva) {
            this.reserva = reserva;
        }
    }


