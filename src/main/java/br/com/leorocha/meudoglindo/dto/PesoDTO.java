package br.com.leorocha.meudoglindo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PesoDTO {

	private Integer id;
	private Integer idAnimal;
	private Double peso;
	private LocalDate dataPesagem; 
}
