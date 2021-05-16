package br.com.leorocha.meudoglindo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="/server")
public class ServerController {
	
	@GetMapping("/status")
	public void getStatus() {
	}
	
}
