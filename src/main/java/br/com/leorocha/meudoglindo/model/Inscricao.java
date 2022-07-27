package br.com.leorocha.meudoglindo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Inscricao {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition="TEXT")
    private String inscricao;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    private LocalDateTime dataInscricao;

    private boolean ativo  = true;
    private LocalDateTime dataCancelamento;
    private String userAgent;
}
