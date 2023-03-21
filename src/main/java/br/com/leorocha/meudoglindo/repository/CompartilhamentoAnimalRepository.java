package br.com.leorocha.meudoglindo.repository;

import br.com.leorocha.meudoglindo.enums.PermissaoCompartilhamento;
import br.com.leorocha.meudoglindo.enums.StatusCompartilhamento;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Arquivo;
import br.com.leorocha.meudoglindo.model.CompartilhamentoAnimal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompartilhamentoAnimalRepository extends CrudRepository<CompartilhamentoAnimal, Integer> {
    CompartilhamentoAnimal findByCodigo(String codigo);

    List<CompartilhamentoAnimal> findByUsuarioIdAndStatusAndPermissao(Integer id, StatusCompartilhamento compartilhado, PermissaoCompartilhamento permissao);

    List<CompartilhamentoAnimal> findByAnimalIdAndUsuarioIdAndStatus(Integer id, Integer id1, StatusCompartilhamento compartilhado);

    Boolean existsByUsuarioIdAndAnimalIdAndStatusAndPermissao(Integer idUsuario, Integer idAnimal, StatusCompartilhamento compartilhado, PermissaoCompartilhamento permissaoCompartilhamento);
}
