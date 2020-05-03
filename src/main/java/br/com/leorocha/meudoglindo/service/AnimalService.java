package br.com.leorocha.meudoglindo.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.AnimalDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Peso;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.repository.AnimalRepository;

@Service
public class AnimalService {
	@Autowired
	private AnimalRepository repository; 
	@Autowired
	private RequestService requestService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PesoService pesoService;
	
	public void salvar(Animal animal) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		animal.setUsuario(usuario);
		repository.save(animal);
	}
	public void atualizar(AnimalDTO animalDTO) throws AuthenticationException {
		Animal animal = buscar(animalDTO.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(animal.getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		animal.setDataNascimento(animalDTO.getDataNascimento());
		animal.setDataObito(animalDTO.getDataObito());
		animal.setNome(animalDTO.getNome());
		repository.save(animal);
	}
	public Animal buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndUsuarioId(id,usuario.getId()).orElse(null);
	}
	public List<Animal> listar() {
		return (List<Animal>) repository.findAll();
	}
	public List<Animal> listarPorUsuarioId() {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByUsuarioId(usuario.getId());
	}
	public void delete(Integer id) throws AuthenticationException {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		Animal animal = buscar(id);
		if(animal.getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode excluir esse registro");
		}
		List<Peso> listaPeso = this.pesoService.listarPorAnimal(animal.getId());
		this.pesoService.deletarLista(listaPeso);
		repository.deleteById(id); 
	}
}
