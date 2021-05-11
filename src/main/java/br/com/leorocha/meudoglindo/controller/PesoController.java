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

import br.com.leorocha.meudoglindo.dto.PesoDTO;
import br.com.leorocha.meudoglindo.model.Peso;
import br.com.leorocha.meudoglindo.service.PesoService;

@RestController
@RequestMapping(path="/peso")
public class PesoController {
	@Autowired
	private PesoService service; 
	
	@PostMapping
	public void salvar(@RequestBody PesoDTO dto ) throws AuthenticationException {
		service.salvar(dto);
	}
	
	@PutMapping
	public void atualizar(PesoDTO dto) throws AuthenticationException {
		service.atualizar(dto);
	}

	@GetMapping("/{id}")
	public PesoDTO buscar(@PathVariable Integer id) {
		Peso peso=  service.buscar(id);
		if(null != peso) {
			return new PesoDTO(peso.getId(), peso.getAnimal().getId(), peso.getPeso(), peso.getDataPesagem());
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/animal/{idAnimal}")
	public List<PesoDTO> listarAnimal(@PathVariable Integer idAnimal) {
		List<Peso> lista = service.listarPorAnimal(idAnimal);
		List<PesoDTO> dtos = new ArrayList<PesoDTO>();
		lista.forEach(peso -> dtos.add(new PesoDTO(peso.getId(), peso.getAnimal().getId(), peso.getPeso(), peso.getDataPesagem())));
		return dtos; 
	}

	@GetMapping("")
	public List<PesoDTO> listar() {
		List<Peso> lista = service.listarPorUsuario();
		List<PesoDTO> dtos = new ArrayList<PesoDTO>();
		lista.forEach(peso -> dtos.add(new PesoDTO(peso.getId(), peso.getAnimal().getId(), peso.getPeso(), peso.getDataPesagem())));
		return dtos; 
	}
}
