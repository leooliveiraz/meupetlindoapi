package br.com.leorocha.meudoglindo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacinaDTO {

	private Integer id;
	private Integer idAnimal;
	private String nome;
	private LocalDate dataVacina;
	private LocalDate dataProximaVacina;
	private String observacao;
}
