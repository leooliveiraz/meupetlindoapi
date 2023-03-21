package br.com.leorocha.meudoglindo.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import br.com.leorocha.meudoglindo.enums.PermissaoCompartilhamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.ExameDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Exame;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.repository.ExameRepository;

@Service
public class ExameService {
	@Autowired
	private RequestService requestService;
	@Autowired
	private AnimalService animalService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ExameRepository repository;
	@Autowired
	private CompartilharAnimalService compartilharAnimalService;

	public void salvar(ExameDTO dto) throws AuthenticationException {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		Boolean permissao = compartilharAnimalService.estaCompartilhado(animal.getId(), usuario.getId(), PermissaoCompartilhamento.EDITAR);
		if(animal.getUsuario().getId() != usuario.getId() && !permissao) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		Exame exame = new Exame(null, dto.getNome(), dto.getDataExame(), animal);
		repository.save(exame);		
	}


	public void atualizar(ExameDTO dto) throws AuthenticationException {
		Exame exame = buscar(dto.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(exame.getAnimal().getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		exame.setDataExame(dto.getDataExame());
		exame.setNome(dto.getNome());
		repository.save(exame);
	}
	
	public Exame buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
	}
	
	public List<Exame> listar() {
		return (List<Exame>) repository.findAllByOrderByDataExameAsc();
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public List<Exame> listarPorAnimal(Integer idAnimal) throws AuthenticationException {
		if(animalService.validarAcessoAoAnimal(idAnimal)){
			return repository.findByAnimalIdOrderByDataExameAsc(idAnimal);
		} else {
			throw new AuthenticationException("Você não pode ver essas informações.");
		}
	}

	public void deletarLista(List<Exame> listaExame) {
		this.repository.deleteAll(listaExame);
		
	}
	public List<Exame> listarPorUsuario() {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<Exame>) repository.findByAnimalUsuarioIdOrderByDataExameAsc(usuario.getId());
	}

}
