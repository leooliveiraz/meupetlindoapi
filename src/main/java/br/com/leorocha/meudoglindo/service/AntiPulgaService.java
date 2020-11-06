package br.com.leorocha.meudoglindo.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.AntiPulgaDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.model.AntiPulga;
import br.com.leorocha.meudoglindo.repository.AntiPulgaRepository;

@Service
public class AntiPulgaService {
	@Autowired
	private RequestService requestService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AnimalService animalService; 
	@Autowired
	private AntiPulgaRepository repository; 
	
	public void salvar(AntiPulgaDTO dto) throws AuthenticationException {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(animal.getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		AntiPulga antiPulga = new AntiPulga(null, dto.getNome(), dto.getDataAntiPulga(), dto.getDataProxima(), animal);
		repository.save(antiPulga);
	}
	public void atualizar(AntiPulgaDTO dto) throws AuthenticationException {
		AntiPulga antiPulga = buscar(dto.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(antiPulga.getAnimal().getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		antiPulga.setDataAntiPulga(dto.getDataAntiPulga());
		antiPulga.setDataProxima(dto.getDataProxima());
		antiPulga.setNome(antiPulga.getNome());
		repository.save(antiPulga);
	}
	
	public AntiPulga buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	public List<AntiPulga> listarPorAnimal(Integer idAnimal) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<AntiPulga>) repository.findByAnimalIdAndAnimalUsuarioIdOrderByDataAntiPulgaDesc(idAnimal, usuario.getId());
	}
	public void deletarLista(List<AntiPulga> listaAntiPulga) {
		this.repository.deleteAll(listaAntiPulga);
		
	}
	public List<AntiPulga> listarPorUsuario() {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<AntiPulga>) repository.findByAnimalUsuarioIdOrderByDataAntiPulgaDesc(usuario.getId());
	}
}
