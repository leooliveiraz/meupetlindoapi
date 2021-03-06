package br.com.leorocha.meudoglindo.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.VacinaDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.model.Vacina;
import br.com.leorocha.meudoglindo.repository.VacinaRepository;

@Service
public class VacinaService {
	@Autowired
	private RequestService requestService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AnimalService animalService; 
	@Autowired
	private VacinaRepository repository; 
	
	public void salvar(VacinaDTO dto) throws AuthenticationException {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(animal.getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		Vacina vacina = new Vacina(null, dto.getNome(), dto.getDataVacina(), dto.getDataProximaVacina(), animal);
		repository.save(vacina);
	}
	public void atualizar(VacinaDTO dto) throws AuthenticationException {
		Vacina vacina = buscar(dto.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(vacina.getAnimal().getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		vacina.setDataVacina(dto.getDataVacina());
		vacina.setDataProximaVacina(dto.getDataProximaVacina());
		vacina.setNome(vacina.getNome());
		repository.save(vacina);
	}
	
	public Vacina buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	public List<Vacina> listarPorAnimal(Integer idAnimal) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<Vacina>) repository.findByAnimalIdAndAnimalUsuarioIdOrderByDataVacinaDesc(idAnimal, usuario.getId());
	}
	public void deletarLista(List<Vacina> listaVacina) {
		this.repository.deleteAll(listaVacina);
		
	}
	public List<Vacina> listarPorUsuario() {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<Vacina>) repository.findByAnimalUsuarioIdOrderByDataVacinaDesc(usuario.getId());
	}
}
