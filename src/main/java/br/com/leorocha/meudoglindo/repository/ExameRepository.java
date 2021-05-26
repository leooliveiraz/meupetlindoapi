package br.com.leorocha.meudoglindo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Exame;

@Repository
public interface ExameRepository extends CrudRepository<Exame, Integer> {

	public Optional<Exame> findByIdAndAnimalUsuarioId(Integer id, Integer id2);

	public List<Exame> findAllByOrderByDataExameAsc();

	public List<Exame> findByAnimalIdAndAnimalUsuarioIdOrderByDataExameAsc(Integer idAnimal, Integer id);

	public List<Exame> findByAnimalUsuarioIdOrderByDataExameAsc(Integer id);

	List<Exame> findByAnimalIdOrderByDataExameAsc(Integer idAnimal);
}
