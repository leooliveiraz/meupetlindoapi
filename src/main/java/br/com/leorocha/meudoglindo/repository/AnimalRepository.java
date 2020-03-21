package br.com.leorocha.meudoglindo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Animal;
@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {
}
