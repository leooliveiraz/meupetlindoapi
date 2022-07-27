package br.com.leorocha.meudoglindo.repository;

import br.com.leorocha.meudoglindo.enums.StatusCompartilhamento;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Inscricao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends CrudRepository<Inscricao, Integer> {

    Inscricao findByInscricao(String json);

    boolean existsByInscricao(String json);

    List<Inscricao> findByUsuarioId(Integer id);
}
