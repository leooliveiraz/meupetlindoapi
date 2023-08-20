package br.com.leorocha.meudoglindo.service;

import br.com.leorocha.meudoglindo.dto.ConsultaDTO;
import br.com.leorocha.meudoglindo.enums.PermissaoCompartilhamento;
import br.com.leorocha.meudoglindo.model.*;
import br.com.leorocha.meudoglindo.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultaService {
	@Autowired
	private RequestService requestService;
	@Autowired
	private AnimalService animalService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ConsultaRepository repository;
	@Autowired
	private CompartilharAnimalService compartilharAnimalService;
	@Autowired
	private InscricaoService inscricaoService;

	public void salvar(ConsultaDTO dto) throws AuthenticationException {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		Boolean permissao = compartilharAnimalService.estaCompartilhado(animal.getId(), usuario.getId(), PermissaoCompartilhamento.EDITAR);
		if (animal.getUsuario().getId() != usuario.getId() && !permissao) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		Consulta consulta = new Consulta(null, dto.getLocal(), dto.getProfissional(), dto.getDataConsulta(), dto.getProximaConsulta(), dto.getObservacao(), animal);
		repository.save(consulta);
	}


	public void atualizar(ConsultaDTO dto) throws AuthenticationException {
		Consulta consulta = buscar(dto.getId());
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		if (consulta.getAnimal().getUsuario().getId() != usuario.getId()) {
			throw new AuthenticationException("Você não pode alterar esse registro");
		}
		consulta.setDataConsulta(dto.getDataConsulta());
		consulta.setProximaConsulta(dto.getProximaConsulta());
		consulta.setLocal(dto.getLocal());
		consulta.setProfissional(dto.getProfissional());
		consulta.setObservacao(dto.getObservacao());
		repository.save(consulta);
	}

	public Consulta buscar(Integer id) {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
	}

	public List<Consulta> listar() {
		return (List<Consulta>) repository.findAllByOrderByDataConsultaAsc();
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public List<Consulta> listarPorAnimal(Integer idAnimal) throws AuthenticationException {
		if (animalService.validarAcessoAoAnimal(idAnimal)) {
			return repository.findByAnimalIdOrderByDataConsultaAsc(idAnimal);
		} else {
			throw new AuthenticationException("Você não pode ver essas informações.");
		}
	}

	public void deletarLista(List<Consulta> consultaList) {
		this.repository.deleteAll(consultaList);

	}

	public List<Consulta> listarPorUsuario() {
		Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
		return (List<Consulta>) repository.findByAnimalUsuarioIdOrderByDataConsultaAsc(usuario.getId());
	}

	public List<Consulta> emQuantosDias(int qtdDias) {
		LocalDate dataEscolhida = LocalDate.now();
		dataEscolhida = dataEscolhida.plusDays(qtdDias);
		return repository.findByProximaConsultaAndAnimalDataObitoIsNull(dataEscolhida);
	}

	public void notificarMedicamento(int diferencaDias) {
		List<Consulta> consultas = emQuantosDias(diferencaDias);
		consultas.forEach(consulta -> {
			Integer idUsuario = consulta.getAnimal().getUsuario().getId();
			List<Inscricao> listaInscricao = inscricaoService.listByUsuarioId(idUsuario);
			listaInscricao.forEach(inscricao -> {
				inscricaoService.prepareSendNotification("MEU PET LINDO - HORA DA CONSULTA",
						"Não se esqueça, está chegando o dia da consulta do seu pet: %s!"
								.formatted(consulta.getAnimal().getNome()),
						"aviso-consulta",
						"Abrir",
						consulta.getAnimal().getId(),
						inscricao);
			});
		});
	}
}
