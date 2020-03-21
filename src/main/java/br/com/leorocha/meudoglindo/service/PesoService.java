package br.com.leorocha.meudoglindo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.PesoDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Peso;
import br.com.leorocha.meudoglindo.repository.PesoRepository;

@Service
public class PesoService {
	@Autowired
	private AnimalService animalService; 
	@Autowired
	private PesoRepository repository; 
	
	public void salvar(PesoDTO dto) {
		Animal animal = animalService.buscar(dto.getIdAnimal());
		Peso peso = new Peso(null, dto.getPeso(), dto.getDataPesagem(), animal);
		repository.save(peso);
	}
	public void atualizar(PesoDTO dto) {
		Peso peso = buscar(dto.getId());
		peso.setDataPesagem(dto.getDataPesagem());
		peso.setPeso(peso.getPeso());
		repository.save(peso);
	}
	
	public Peso buscar(Integer id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Peso> listar() {
		return (List<Peso>) repository.findAll();
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
