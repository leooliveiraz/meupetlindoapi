package br.com.leorocha.meudoglindo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Vermifugo;
@Repository
public interface VermifugoRepository extends CrudRepository<Vermifugo, Integer> {

	Optional<Vermifugo> findByIdAndAnimalUsuarioId(Integer idPeso, Integer idUsuario);
	
	List<Vermifugo> findByAnimalIdAndAnimalUsuarioIdOrderByDataVermifugoDesc(Integer idAnimal, Integer id);

	List<Vermifugo> findByAnimalUsuarioIdOrderByDataVermifugoDesc(Integer id);

    List<Vermifugo> findByAnimalIdOrderByDataVermifugoDesc(Integer idAnimal);
}
