package br.com.leorocha.meudoglindo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExameDTO {
	private Integer id;
	private String nome;
	private LocalDate dataExame;
	private Integer idAnimal;
	private String observacao;
}
