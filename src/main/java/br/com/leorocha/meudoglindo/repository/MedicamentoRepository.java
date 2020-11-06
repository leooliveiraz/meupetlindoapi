package br.com.leorocha.meudoglindo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Medicamento;
@Repository
public interface MedicamentoRepository extends CrudRepository<Medicamento, Integer> {

	Optional<Medicamento> findByIdAndAnimalUsuarioId(Integer idPeso, Integer idUsuario);
	
	List<Medicamento> findByAnimalIdAndAnimalUsuarioIdOrderByDataMedicamentoDesc(Integer idAnimal, Integer id);

	List<Medicamento> findByAnimalUsuarioIdOrderByDataMedicamentoDesc(Integer id);
}
