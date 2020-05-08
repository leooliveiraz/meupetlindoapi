package br.com.leorocha.meudoglindo.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.VermifugoDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.model.Vermifugo;
import br.com.leorocha.meudoglindo.repository.VermifugoRepository;

@Service
public class VermifugoService {
	@Autowired
	private RequestService requestService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AnimalService animalService; 
	@Autowired
	private VermifugoRepository repository; 
	
	public void salvar(VermifugoDTO dto) throws AuthenticationException {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(animal.getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		Vermifugo vermifugo = new Vermifugo(null, dto.getNome(), dto.getDataVermifugo(), dto.getDataProximoVermifugo(), animal);
		repository.save(vermifugo);
	}
	public void atualizar(VermifugoDTO dto) throws AuthenticationException {
		Vermifugo vermifugo = buscar(dto.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(vermifugo.getAnimal().getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		vermifugo.setDataVermifugo(dto.getDataVermifugo());
		vermifugo.setDataProximoVermifugo(dto.getDataProximoVermifugo());
		vermifugo.setNome(vermifugo.getNome());
		repository.save(vermifugo);
	}
	
	public Vermifugo buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	public List<Vermifugo> listarPorAnimal(Integer idAnimal) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<Vermifugo>) repository.findByAnimalIdAndAnimalUsuarioIdOrderByDataVermifugoDesc(idAnimal, usuario.getId());
	}
	public void deletarLista(List<Vermifugo> listaVermifugo) {
		this.repository.deleteAll(listaVermifugo);
		
	}
}
