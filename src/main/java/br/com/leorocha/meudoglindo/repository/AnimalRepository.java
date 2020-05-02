package br.com.leorocha.meudoglindo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Animal;
@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {

	List<Animal> findByUsuarioId(Integer id);
	
	Optional<Animal> findByIdAndUsuarioId(Integer idAnimal, Integer idUsuario);
	
}
