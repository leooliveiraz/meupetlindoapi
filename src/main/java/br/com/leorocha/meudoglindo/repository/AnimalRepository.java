package br.com.leorocha.meudoglindo.repository;

import java.util.List;
import java.util.Optional;

import br.com.leorocha.meudoglindo.enums.StatusCompartilhamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Animal;
@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {

	List<Animal> findByUsuarioIdOrderByNome(Integer id);
	
	Optional<Animal> findByIdAndUsuarioId(Integer idAnimal, Integer idUsuario);

	@Query("select distinct ca.animal from CompartilhamentoAnimal ca where ca.animal.id = ?1 and ca.usuario.id = ?2 and ca.status = ?3 ")
	Optional<Animal> findAnimalCompartilhado(Integer idAnimal, Integer idUsuario, StatusCompartilhamento status);
}
