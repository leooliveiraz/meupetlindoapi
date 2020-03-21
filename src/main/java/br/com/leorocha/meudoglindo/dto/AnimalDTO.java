package br.com.leorocha.meudoglindo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AnimalDTO {
	private Integer id;
	private String nome;
	private LocalDate dataNascimento;	
}
