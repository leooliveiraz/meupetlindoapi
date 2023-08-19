package br.com.leorocha.meudoglindo.controller;

import br.com.leorocha.meudoglindo.dto.ConsultaDTO;
import br.com.leorocha.meudoglindo.dto.ExameDTO;
import br.com.leorocha.meudoglindo.model.Consulta;
import br.com.leorocha.meudoglindo.model.Exame;
import br.com.leorocha.meudoglindo.service.ConsultaService;
import br.com.leorocha.meudoglindo.service.ExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/consulta")
public class ConsultaController {

	@Autowired
	private ConsultaService service;
	
	@PostMapping
	public void salvar(@RequestBody ConsultaDTO dto ) throws AuthenticationException {
		service.salvar(dto);
	}
	
	@PutMapping
	public void atualizar(ConsultaDTO dto) throws AuthenticationException {
		service.atualizar(dto);
	}

	@GetMapping("/{id}")
	public ConsultaDTO buscar(@PathVariable Integer id) {
		Consulta consulta=  service.buscar(id);
		if(null != consulta) {
			return new ConsultaDTO(consulta.getId(),consulta.getLocal(), consulta.getProfissional(), consulta.getDataConsulta(), consulta.getProximaConsulta(), consulta.getObservacao(), consulta.getAnimal().getId() );
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/animal/{idAnimal}")
	public List<ConsultaDTO> listarAnimal(@PathVariable Integer idAnimal) throws AuthenticationException {
		List<Consulta> lista = service.listarPorAnimal(idAnimal);
		List<ConsultaDTO> dtos = new ArrayList<ConsultaDTO>();
		lista.forEach(consulta -> dtos.add(new ConsultaDTO(consulta.getId(),consulta.getLocal(), consulta.getProfissional(), consulta.getDataConsulta(), consulta.getProximaConsulta(), consulta.getObservacao(), consulta.getAnimal().getId() )));
		return dtos; 
	}
	@GetMapping
	public List<ConsultaDTO> listar() {
		List<Consulta> lista = service.listarPorUsuario();
		List<ConsultaDTO> dtos = new ArrayList<ConsultaDTO>();
		lista.forEach(consulta -> dtos.add(new ConsultaDTO(consulta.getId(),consulta.getLocal(), consulta.getProfissional(), consulta.getDataConsulta(), consulta.getProximaConsulta(), consulta.getObservacao(), consulta.getAnimal().getId() )));
		return dtos; 
	}
}
