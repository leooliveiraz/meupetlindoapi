package br.com.leorocha.meudoglindo.controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.leorocha.meudoglindo.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.leorocha.meudoglindo.dto.SubscriptionDTO;
import br.com.leorocha.meudoglindo.service.RestService;


@RestController
@RequestMapping(path="/web-push")
public class WebPushController {
	@Autowired
	private RestService restService;
	@Autowired
	private InscricaoService inscricaoService;

	List<String> inscricoes = new ArrayList<String>();
	
	@PostMapping
	public void inscrever(@RequestBody SubscriptionDTO inscricao) {
		Gson gson = new Gson();
		String dtoJson = gson.toJson(inscricao);
			this.inscricoes.add(dtoJson);
			System.out.println(inscricao.toString());
			inscricaoService.salvar(dtoJson);
	}
	
}
