package br.com.leorocha.meudoglindo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.repository.AnimalRepository;

@Service
public class AnimalService {
	@Autowired
	private AnimalRepository repository; 
	
	public void salvar(Animal animal) {
		repository.save(animal);
	}
	public void atualizar(Animal animal) {
		repository.save(animal);
	}
	public Animal buscar(Integer id) {
		return repository.findById(id).orElse(null);
	}
	public List<Animal> listar() {
		return (List<Animal>) repository.findAll();
	}
	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
