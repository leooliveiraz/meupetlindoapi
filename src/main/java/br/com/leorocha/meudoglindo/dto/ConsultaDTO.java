package br.com.leorocha.meudoglindo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDTO {
    private Integer id;
    private String local;
    private String profissional;
    private LocalDate dataConsulta;
    private LocalDate proximaConsulta;
    private String observacao;
    private Integer idAnimal;

}
