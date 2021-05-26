package br.com.leorocha.meudoglindo.dto;

import br.com.leorocha.meudoglindo.model.CompartilhamentoAnimal;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DadosCompartilhamentoAnimalDTO {

    private String codigo;
    private String nomeDono;
    private String nomeAnimal;
    private LocalDateTime dataGeracaoToken;
    private LocalDateTime dataMaximaCompartilhar;
    private String arquivo;

    public DadosCompartilhamentoAnimalDTO(CompartilhamentoAnimal compartilhamentoAnimal){
        this.codigo = compartilhamentoAnimal.getCodigo();
        this.nomeDono = compartilhamentoAnimal.getAnimal().getUsuario().getName();
        this.nomeAnimal = compartilhamentoAnimal.getAnimal().getNome();
        this.dataGeracaoToken = compartilhamentoAnimal.getData();
        this.dataMaximaCompartilhar = this.dataGeracaoToken.plusDays(1);
        this.arquivo = compartilhamentoAnimal.getAnimal().getImagem().getCripto();
    }

}
