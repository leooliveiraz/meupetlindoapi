package br.com.leorocha.meudoglindo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leorocha.meudoglindo.dto.AnimalDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.service.AnimalService;

@RestController
@RequestMapping(path="/animal")
public class AnimalController {
	@Autowired
	private AnimalService service; 
	
	@PostMapping
	public void salvar(@RequestBody AnimalDTO dto ) {
		Animal animal = new Animal(null, dto.getNome(), dto.getDataNascimento(),dto.getDataObito());
		service.salvar(animal);
	}
	@PutMapping
	public void atualizar( @RequestBody Animal animal) {
		service.atualizar(animal);
	}

	@GetMapping("/{id}")
	public Animal buscar(@PathVariable Integer id) {
		return service.buscar(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
	
	@GetMapping()
	public List<Animal> listar() {
		return service.listar();
	}
}
