package br.com.leorocha.meudoglindo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;

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
	public Integer salvar(@RequestBody AnimalDTO dto) throws AuthenticationException {
		if(dto.getId() != null) {
			service.atualizar(dto);
			return dto.getId();
		} else {
			Animal animal = new Animal(null, dto.getNome(), dto.getDataNascimento(), dto.getDataAdocao(), dto.getDataObito(), null,null);
			service.salvar(animal);
			return animal.getId(); 
		}
	}
	@PutMapping
	public void atualizar( @RequestBody AnimalDTO animalDTO) throws AuthenticationException {	
		service.atualizar(animalDTO);
	}

	@GetMapping("/{id}")
	public AnimalDTO buscar(@PathVariable Integer id) {
		Animal animal = service.buscar(id);
		AnimalDTO dto = new AnimalDTO(animal.getId(), animal.getNome(), animal.getDataNascimento(), animal.getDataAdocao(), animal.getDataObito(), animal.getImagem() != null ? animal.getImagem().getCripto() : null);
		return dto;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) throws AuthenticationException {
		service.delete(id);
	}

	@GetMapping()
	public List<AnimalDTO> listar() {
		List<AnimalDTO> listaDTO = new ArrayList<AnimalDTO>();
		List<Animal> listAnimal = service.listarPorUsuarioId();
		listAnimal.forEach(animal -> {
			listaDTO.add(new AnimalDTO(animal.getId(), animal.getNome(), animal.getDataNascimento(), animal.getDataAdocao(), animal.getDataObito(), animal.getImagem() != null ? animal.getImagem().getCripto() : null));
		});
		return listaDTO;
	}

	@PutMapping("/uploadImagem/{id}")
	public void uploadImagem( @PathVariable Integer id, @RequestBody String imagem) throws AuthenticationException {	
		service.uploadImagem(id, imagem );
	}
}
