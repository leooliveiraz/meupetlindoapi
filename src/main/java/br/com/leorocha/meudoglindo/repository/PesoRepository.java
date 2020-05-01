package br.com.leorocha.meudoglindo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Peso;
@Repository
public interface PesoRepository extends CrudRepository<Peso, Integer> {


	List<Peso> findAllByOrderByDataPesagemAsc();

	List<Peso> findByAnimalIdOrderByDataPesagemAsc(Integer idAnimal);
}