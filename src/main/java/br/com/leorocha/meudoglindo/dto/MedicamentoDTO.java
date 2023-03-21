package br.com.leorocha.meudoglindo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoDTO {

	private Integer id;
	private Integer idAnimal;
	private String nome;
	private String tipoMedicamento;
	private LocalDate dataMedicamento;
	private LocalDate dataProxima;
	private String observacao;
}
