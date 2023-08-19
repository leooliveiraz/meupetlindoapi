package br.com.leorocha.meudoglindo.repository;

import br.com.leorocha.meudoglindo.model.Consulta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends CrudRepository<Consulta, Integer> {
    Optional<Consulta> findByIdAndAnimalUsuarioId(Integer id, Integer id1);

    List<Consulta> findAllByOrderByDataConsultaAsc();

    List<Consulta> findByAnimalIdOrderByDataConsultaAsc(Integer idAnimal);

    Object findByAnimalUsuarioIdOrderByDataConsultaAsc(Integer id);
}
