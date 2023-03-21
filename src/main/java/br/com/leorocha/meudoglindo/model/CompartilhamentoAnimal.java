package br.com.leorocha.meudoglindo.model;

import br.com.leorocha.meudoglindo.enums.PermissaoCompartilhamento;
import br.com.leorocha.meudoglindo.enums.StatusCompartilhamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompartilhamentoAnimal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "animal")
    private Animal animal;

    private String codigo;
    private LocalDateTime data; //geracao

    @Enumerated(EnumType.ORDINAL)
    private StatusCompartilhamento status;

    @Enumerated(EnumType.STRING)
    private PermissaoCompartilhamento permissao;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario; //compartilhado

}
