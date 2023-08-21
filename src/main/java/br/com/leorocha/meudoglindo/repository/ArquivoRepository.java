package br.com.leorocha.meudoglindo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Arquivo;
@Repository
public interface ArquivoRepository extends CrudRepository<Arquivo, Integer> {	
	Optional<Arquivo> findByCripto(String cripto);
    @Query(value = "select a.cripto from Arquivo a where a.id = :id")
    String findCriptoById(@Param("id") Integer id);
}
