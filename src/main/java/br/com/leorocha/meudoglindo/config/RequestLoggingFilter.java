package br.com.leorocha.meudoglindo.config;

import java.io.IOException;
import java.util.Base64;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

import br.com.leorocha.meudoglindo.dto.UserDTO;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.service.RestService;
import br.com.leorocha.meudoglindo.service.TokenService;
import br.com.leorocha.meudoglindo.service.UsuarioService;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
	@Autowired
	private RestService restService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String uri = request.getRequestURI();

		if(!uri.startsWith("/arquivo/") && !uri.startsWith("/web-push") && !uri.startsWith("/server/status") ) {
			String authorization = request.getHeader("Authorization");
			boolean autenticado = this.autenticar(authorization);
			if(!autenticado) {
				throw new AuthenticationException("Autenticação Inválida");
			}
		}

		//continue filtering
		filterChain.doFilter(request, response);
	}

	private Boolean autenticar(String token) {
		token = token.replace("Bearer ", "");
		try {
			if(this.tokenService.tokenEncontrada(token)) {
				return true;
			} else {
				DecodedJWT jwt = JWT.decode(token);    	    
				byte[] decodedBytes = Base64.getDecoder().decode(jwt.getPayload());
				String decodedString = new String(decodedBytes);
				UserDTO dto = new Gson().fromJson(decodedString, UserDTO.class);
				Boolean tokenValido = validatingTokenInGoogle(token);
				if(tokenValido) {
					if(!usuarioService.usuarioExiste(dto.getSub())) {
						Usuario usuario = new Usuario(dto);
						usuarioService.salvar(usuario);
					}
					this.tokenService.adicionar(token, dto);
					return true;
				} else {
					if(usuarioService.usuarioExiste(dto.getSub())) {
						this.tokenService.adicionar(token, dto);
						return true;
					}
					return false;
				}
			}
		} catch (JWTDecodeException exception){
			//Invalid token
			exception.printStackTrace();
			return false;
		}
	}

	private Boolean validatingTokenInGoogle(String token) {
		try {
			String response = restService.get("https://oauth2.googleapis.com/tokeninfo?id_token="+token);
			return response != null ? true : false;    	

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}


}