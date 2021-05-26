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

import br.com.leorocha.meudoglindo.dto.AntiPulgaDTO;
import br.com.leorocha.meudoglindo.model.AntiPulga;
import br.com.leorocha.meudoglindo.service.AntiPulgaService;

@RestController
@RequestMapping(path="/antipulga")
public class AntiPulgaController {
	@Autowired
	private AntiPulgaService service; 
	
	@PostMapping
	public void salvar(@RequestBody AntiPulgaDTO dto ) throws AuthenticationException {
		service.salvar(dto);
	}
	@PutMapping
	public void atualizar(AntiPulgaDTO dto) throws AuthenticationException {
		service.atualizar(dto);
	}

	@GetMapping("/{id}")
	public AntiPulgaDTO buscar(@PathVariable Integer id) {
		AntiPulga antiPulga=  service.buscar(id);
		if(null != antiPulga) {
			return new AntiPulgaDTO(antiPulga.getId(), antiPulga.getAnimal().getId(), antiPulga.getNome(), antiPulga.getDataAntiPulga(), antiPulga.getDataProxima());
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/animal/{idAnimal}")
	public List<AntiPulgaDTO> listarAnimal(@PathVariable Integer idAnimal) throws AuthenticationException {
		List<AntiPulga> lista = service.listarPorAnimal(idAnimal);
		List<AntiPulgaDTO> dtos = new ArrayList<AntiPulgaDTO>();
		lista.forEach(antiPulga -> dtos.add(new AntiPulgaDTO(antiPulga.getId(), antiPulga.getAnimal().getId(), antiPulga.getNome(), antiPulga.getDataAntiPulga(), antiPulga.getDataProxima())));
		return dtos; 
	}
	@GetMapping
	public List<AntiPulgaDTO> listar() {
		List<AntiPulga> lista = service.listarPorUsuario();
		List<AntiPulgaDTO> dtos = new ArrayList<AntiPulgaDTO>();
		lista.forEach(antiPulga -> dtos.add(new AntiPulgaDTO(antiPulga.getId(), antiPulga.getAnimal().getId(), antiPulga.getNome(), antiPulga.getDataAntiPulga(), antiPulga.getDataProxima())));
		return dtos; 
	}
}
