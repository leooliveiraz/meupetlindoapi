package br.com.leorocha.meudoglindo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Vacina;
@Repository
public interface VacinaRepository extends CrudRepository<Vacina, Integer> {

	Optional<Vacina> findByIdAndAnimalUsuarioId(Integer idPeso, Integer idUsuario);
	
	List<Vacina> findByAnimalIdAndAnimalUsuarioIdOrderByDataVacinaDesc(Integer idAnimal, Integer id);

	List<Vacina> findByAnimalUsuarioIdOrderByDataVacinaDesc(Integer id);

    List<Vacina> findByAnimalIdOrderByDataVacinaDesc(Integer idAnimal);

    List<Vacina> findByDataProximaVacina(LocalDate dataEscolhida);

	List<Vacina> findByDataProximaVacinaAndAnimalDataObitoIsNull(LocalDate dataEscolhida);
}
