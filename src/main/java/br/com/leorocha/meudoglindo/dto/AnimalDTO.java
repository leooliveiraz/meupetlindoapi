package br.com.leorocha.meudoglindo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
	private Integer id;
	private String nome;
	private LocalDate dataNascimento;
	private LocalDate dataObito;	
	private String idArquivo;
}
