package br.com.leorocha.meudoglindo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Peso;
@Repository
public interface PesoRepository extends CrudRepository<Peso, Integer> {

	Optional<Peso> findByIdAndAnimalUsuarioId(Integer idPeso, Integer idUsuario);
	
	List<Peso> findAllByOrderByDataPesagemAsc();

	List<Peso> findByAnimalIdOrderByDataPesagemAsc(Integer idAnimal);

	List<Peso> findByAnimalIdAndAnimalUsuarioIdOrderByDataPesagemAsc(Integer idAnimal, Integer id);
}
