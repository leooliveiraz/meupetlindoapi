package br.com.leorocha.meudoglindo.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import br.com.leorocha.meudoglindo.enums.PermissaoCompartilhamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.PesoDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Peso;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.repository.PesoRepository;

@Service
public class PesoService {
	@Autowired
	private RequestService requestService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AnimalService animalService; 
	@Autowired
	private PesoRepository repository;
	@Autowired
	private CompartilharAnimalService compartilharAnimalService;
	
	public void salvar(PesoDTO dto) throws AuthenticationException {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		Boolean permissao = compartilharAnimalService.estaCompartilhado(animal.getId(), usuario.getId(), PermissaoCompartilhamento.EDITAR);
		if(animal.getUsuario().getId() != usuario.getId() && !permissao) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		Peso peso = new Peso(null, dto.getPeso(), dto.getDataPesagem(), animal);
		repository.save(peso);
	}
	public void atualizar(PesoDTO dto) throws AuthenticationException {
		Peso peso = buscar(dto.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(peso.getAnimal().getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		peso.setDataPesagem(dto.getDataPesagem());
		peso.setPeso(peso.getPeso());
		repository.save(peso);
	}
	
	public Peso buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
	}
	
	public List<Peso> listar() {
		return (List<Peso>) repository.findAllByOrderByDataPesagemAsc();
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public List<Peso> listarPorAnimal(Integer idAnimal) throws AuthenticationException {
		if(animalService.validarAcessoAoAnimal(idAnimal)){
			return repository.findByAnimalIdOrderByDataPesagemAsc(idAnimal);
		} else {
			throw new AuthenticationException("Você não pode ver essas informações.");
		}
	}

	public void deletarLista(List<Peso> listaPeso) {
		this.repository.deleteAll(listaPeso);
		
	}
	public List<Peso> listarPorUsuario() {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<Peso>) repository.findByAnimalUsuarioIdOrderByDataPesagemAsc(usuario.getId());
	}
}
