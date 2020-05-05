package br.com.leorocha.meudoglindo.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leorocha.meudoglindo.service.ArquivoService;

@RestController
@RequestMapping(path="/arquivo")
public class ArquivoController {
	@Autowired
	private ArquivoService service; 
	
	

	@GetMapping(value = "/{token}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] buscar(@PathVariable String  token) throws FileNotFoundException {		
		return service.getArquivo(token);
	}
}
