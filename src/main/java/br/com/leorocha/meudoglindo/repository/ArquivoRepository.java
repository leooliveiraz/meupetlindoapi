package br.com.leorocha.meudoglindo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Arquivo;
@Repository
public interface ArquivoRepository extends CrudRepository<Arquivo, Integer> {	
	Optional<Arquivo> findByCripto(String cripto);
	
}
