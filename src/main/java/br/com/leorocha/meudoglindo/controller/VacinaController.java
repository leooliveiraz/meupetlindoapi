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

import br.com.leorocha.meudoglindo.dto.VacinaDTO;
import br.com.leorocha.meudoglindo.model.Vacina;
import br.com.leorocha.meudoglindo.service.VacinaService;

@RestController
@RequestMapping(path="/vacina")
public class VacinaController {
	@Autowired
	private VacinaService service; 
	
	@PostMapping
	public void salvar(@RequestBody VacinaDTO dto ) throws AuthenticationException {
		service.salvar(dto);
	}
	@PutMapping
	public void atualizar(VacinaDTO dto) throws AuthenticationException {
		service.atualizar(dto);
	}

	@GetMapping("/{id}")
	public VacinaDTO buscar(@PathVariable Integer id) {
		Vacina vacina=  service.buscar(id);
		if(null != vacina) {
			return new VacinaDTO(vacina.getId(), vacina.getAnimal().getId(), vacina.getNome(), vacina.getDataVacina(), vacina.getDataProximaVacina());
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/animal/{idAnimal}")
	public List<VacinaDTO> listarAnimal(@PathVariable Integer idAnimal) throws AuthenticationException {
		List<Vacina> lista = service.listarPorAnimal(idAnimal);
		List<VacinaDTO> dtos = new ArrayList<VacinaDTO>();
		lista.forEach(vacina -> dtos.add(new VacinaDTO(vacina.getId(), vacina.getAnimal().getId(), vacina.getNome(), vacina.getDataVacina(), vacina.getDataProximaVacina())));
		return dtos; 
	}
	@GetMapping
	public List<VacinaDTO> listar() {
		List<Vacina> lista = service.listarPorUsuario();
		List<VacinaDTO> dtos = new ArrayList<VacinaDTO>();
		lista.forEach(vacina -> dtos.add(new VacinaDTO(vacina.getId(), vacina.getAnimal().getId(), vacina.getNome(), vacina.getDataVacina(), vacina.getDataProximaVacina())));
		return dtos; 
	}
}
