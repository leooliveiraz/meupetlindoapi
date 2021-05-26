package br.com.leorocha.meudoglindo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.AntiPulga;
@Repository
public interface AntiPulgaRepository extends CrudRepository<AntiPulga, Integer> {

	Optional<AntiPulga> findByIdAndAnimalUsuarioId(Integer idPeso, Integer idUsuario);
	
	List<AntiPulga>
	findByAnimalIdAndAnimalUsuarioIdOrderByDataAntiPulgaDesc(Integer idAnimal, Integer id);

	List<AntiPulga> findByAnimalUsuarioIdOrderByDataAntiPulgaDesc(Integer id);

	List<AntiPulga> findByAnimalIdOrderByDataAntiPulgaDesc(Integer idAnimal);
}
