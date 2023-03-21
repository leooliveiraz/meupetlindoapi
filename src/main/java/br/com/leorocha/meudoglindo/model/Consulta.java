package br.com.leorocha.meudoglindo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String local;
    private String profissional;
    private LocalDate dataConsulta;
    private LocalDate proximaConsulta;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "animal", foreignKey = @ForeignKey(name = "consulta_fk"))
    private Animal animal;
}