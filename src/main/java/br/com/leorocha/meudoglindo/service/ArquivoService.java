package br.com.leorocha.meudoglindo.service;

import java.io.FileNotFoundException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.model.Arquivo;
import br.com.leorocha.meudoglindo.repository.ArquivoRepository;

@Service
public class ArquivoService {
	@Autowired
	private ArquivoRepository repository; 
	
	public byte[] getArquivo(String cripto) throws FileNotFoundException {
		Optional<Arquivo> optional = repository.findByCripto(cripto);
		if(!optional.isPresent()) {
			throw new FileNotFoundException("Arquivo não encontrado.");
		} else {
			Arquivo arquivo = optional.get();
			return arquivo.getArquivo();
		}
	}

	public Arquivo findById(Integer id){
		return repository.findById(id).get();
	}

	public String findCriptoById(Integer id){
		return repository.findCriptoById(id);
	}
}
