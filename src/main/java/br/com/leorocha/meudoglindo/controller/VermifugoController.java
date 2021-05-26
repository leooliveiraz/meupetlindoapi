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

import br.com.leorocha.meudoglindo.dto.VermifugoDTO;
import br.com.leorocha.meudoglindo.model.Vermifugo;
import br.com.leorocha.meudoglindo.service.VermifugoService;

@RestController
@RequestMapping(path="/vermifugo")
public class VermifugoController {
	@Autowired
	private VermifugoService service; 

	@PostMapping
	public void salvar(@RequestBody VermifugoDTO dto ) throws AuthenticationException {
		service.salvar(dto);
	}
	@PutMapping
	public void atualizar(VermifugoDTO dto) throws AuthenticationException {
		service.atualizar(dto);
	}

	@GetMapping("/{id}")
	public VermifugoDTO buscar(@PathVariable Integer id) {
		Vermifugo vermifugo=  service.buscar(id);
		if(null != vermifugo) {
			return new VermifugoDTO(vermifugo.getId(), vermifugo.getAnimal().getId(), vermifugo.getNome(), vermifugo.getDataVermifugo(), vermifugo.getDataProximoVermifugo());
		} else {
			return null;
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/animal/{idAnimal}")
	public List<VermifugoDTO> listarAnimal(@PathVariable Integer idAnimal) throws AuthenticationException {
		List<Vermifugo> lista = service.listarPorAnimal(idAnimal);
		List<VermifugoDTO> dtos = new ArrayList<VermifugoDTO>();
		lista.forEach(vermifugo -> dtos.add(new VermifugoDTO(vermifugo.getId(), vermifugo.getAnimal().getId(), vermifugo.getNome(), vermifugo.getDataVermifugo(), vermifugo.getDataProximoVermifugo())));
		return dtos; 
	}

	@GetMapping
	public List<VermifugoDTO> listar() {
		List<Vermifugo> lista = service.listarPorUsuario();
		List<VermifugoDTO> dtos = new ArrayList<VermifugoDTO>();
		lista.forEach(vermifugo -> dtos.add(new VermifugoDTO(vermifugo.getId(), vermifugo.getAnimal().getId(), vermifugo.getNome(), vermifugo.getDataVermifugo(), vermifugo.getDataProximoVermifugo())));
		return dtos; 
	}
}
