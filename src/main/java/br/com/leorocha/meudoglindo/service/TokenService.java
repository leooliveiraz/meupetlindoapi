package br.com.leorocha.meudoglindo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.UserDTO;

@Service
public class TokenService {
	private HashMap<String, UserDTO> tokens = new HashMap<String, UserDTO>();
	
	public boolean tokenEncontrada(String authorization) {
		String token = authorization.replace("Bearer ", "");
		if (tokens.containsKey(token)) {
			return true;
		} else {
			return false;
		}
	}

	public void adicionar(String token, UserDTO dto) {
		this.tokens.put(token, dto);
	}
	

	public UserDTO get(String authorization) {
		String token = authorization.replace("Bearer ", "");
		return this.tokens.get(token);
	}
	

	public void removerToken(String token) {
		this.tokens.remove(token);
	}
	public void removerTokenVencida() {
		ZoneId zoneId = ZoneId.systemDefault();
		int nowEpoch =   Integer.parseInt(LocalDateTime.now().atZone(zoneId).toEpochSecond()+"");
		Set<String> tokenList = this.tokens.keySet();
		for(String _token : tokenList) {
			UserDTO user = this.tokens.get(_token);
			if(user.getExp() < nowEpoch) {
				this.removerToken(_token);
			}
		}
	}
	
}
