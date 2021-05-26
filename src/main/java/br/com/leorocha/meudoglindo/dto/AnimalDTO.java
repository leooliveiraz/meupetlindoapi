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
	private LocalDate dataAdocao;
	private LocalDate dataObito;
	private String idArquivo;
	private boolean dono;

	public AnimalDTO(Integer id, String nome, LocalDate dataNascimento, LocalDate dataAdocao, LocalDate dataObito, String idArquivo) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.dataAdocao = dataAdocao;
		this.dataObito = dataObito;
		this.idArquivo = idArquivo;
	}
}
