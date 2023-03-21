package br.com.leorocha.meudoglindo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;
import javax.websocket.server.PathParam;

import br.com.leorocha.meudoglindo.enums.PermissaoCompartilhamento;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.service.CompartilharAnimalService;
import br.com.leorocha.meudoglindo.service.RequestService;
import br.com.leorocha.meudoglindo.service.UsuarioService;
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
	@Autowired
	private CompartilharAnimalService compartilharService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RequestService requestService;



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
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());

		AnimalDTO dto = new AnimalDTO(animal.getId(), animal.getNome(), animal.getDataNascimento(), animal.getDataAdocao(), animal.getDataObito(), animal.getImagem() != null ? animal.getImagem().getCripto() : null);
		dto.setDono(usuario.getId().equals(animal.getUsuario().getId()));
		return dto;
	}
	
	@GetMapping("/tudo")
	public List<AnimalDTO> listarTudo(@PathVariable Integer id) {
		List<Animal> animais = service.listar();
		List<AnimalDTO> dtos = new ArrayList<AnimalDTO>();
		animais.forEach(animal ->{
			AnimalDTO dto = new AnimalDTO(animal.getId(), animal.getNome(), animal.getDataNascimento(), animal.getDataAdocao(), animal.getDataObito(), animal.getImagem() != null ? animal.getImagem().getCripto() : null);
			dtos.add(dto);
		});
		return dtos;
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

	@GetMapping("/compartilhados")
	public List<AnimalDTO> compartilhados(@PathParam("permissao") PermissaoCompartilhamento permissao) throws AuthenticationException {
		List<AnimalDTO> listaDTO = new ArrayList<AnimalDTO>();
		List<Animal> listAnimal = compartilharService.listarCompartilhadosPorUsuario(permissao);
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
