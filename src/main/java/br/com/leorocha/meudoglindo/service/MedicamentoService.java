package br.com.leorocha.meudoglindo.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.MedicamentoDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.model.Medicamento;
import br.com.leorocha.meudoglindo.repository.MedicamentoRepository;

@Service
public class MedicamentoService {
	@Autowired
	private RequestService requestService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AnimalService animalService; 
	@Autowired
	private MedicamentoRepository repository; 
	
	public void salvar(MedicamentoDTO dto) throws AuthenticationException {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(animal.getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		Medicamento medicamento = new Medicamento(null, dto.getNome(),dto.getTipoMedicamento(), dto.getDataMedicamento(), dto.getDataProxima(), animal);
		repository.save(medicamento);
	}
	public void atualizar(MedicamentoDTO dto) throws AuthenticationException {
		Medicamento medicamento = buscar(dto.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if(medicamento.getAnimal().getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		medicamento.setDataMedicamento(dto.getDataMedicamento());
		medicamento.setDataProxima(dto.getDataProxima());
		medicamento.setNome(medicamento.getNome());
		repository.save(medicamento);
	}
	
	public Medicamento buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public List<Medicamento> listarPorAnimal(Integer idAnimal) throws AuthenticationException {
		if(animalService.validarAcessoAoAnimal(idAnimal)){
			return repository.findByAnimalIdOrderByDataMedicamentoDesc(idAnimal);
		} else {
			throw new AuthenticationException("Você não pode ver essas informações.");
		}
	}

	public void deletarLista(List<Medicamento> listaMedicamento) {
		this.repository.deleteAll(listaMedicamento);
		
	}
	public List<Medicamento> listarPorUsuario() {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<Medicamento>) repository.findByAnimalUsuarioIdOrderByDataMedicamentoDesc(usuario.getId());
	}
}
