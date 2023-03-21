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

import br.com.leorocha.meudoglindo.dto.ExameDTO;
import br.com.leorocha.meudoglindo.model.Exame;
import br.com.leorocha.meudoglindo.service.ExameService;

@RestController
@RequestMapping(path="/exames")
public class ExameController {

	@Autowired
	private ExameService service; 
	
	@PostMapping
	public void salvar(@RequestBody ExameDTO dto ) throws AuthenticationException {
		service.salvar(dto);
	}
	
	@PutMapping
	public void atualizar(ExameDTO dto) throws AuthenticationException {
		service.atualizar(dto);
	}

	@GetMapping("/{id}")
	public ExameDTO buscar(@PathVariable Integer id) {
		Exame exame=  service.buscar(id);
		if(null != exame) {
			return new ExameDTO(exame.getId(),exame.getNome(), exame.getDataExame(), exame.getAnimal().getId(), exame.getObservacao());
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/animal/{idAnimal}")
	public List<ExameDTO> listarAnimal(@PathVariable Integer idAnimal) throws AuthenticationException {
		List<Exame> lista = service.listarPorAnimal(idAnimal);
		List<ExameDTO> dtos = new ArrayList<ExameDTO>();
		lista.forEach(exame -> dtos.add(new ExameDTO(exame.getId(), exame.getNome(), exame.getDataExame(),exame.getAnimal().getId(), exame.getObservacao())));
		return dtos; 
	}
	@GetMapping
	public List<ExameDTO> listar() {
		List<Exame> lista = service.listarPorUsuario();
		List<ExameDTO> dtos = new ArrayList<ExameDTO>();
		lista.forEach(exame -> dtos.add(new ExameDTO(exame.getId(), exame.getNome(), exame.getDataExame(), exame.getAnimal().getId(), exame.getObservacao())));
		return dtos; 
	}
}
