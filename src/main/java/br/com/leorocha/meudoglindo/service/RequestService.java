package br.com.leorocha.meudoglindo.service;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

import br.com.leorocha.meudoglindo.dto.UserDTO;

@Service
public class RequestService {
	@Autowired
	private TokenService tokenService;
	
	 public HttpServletRequest getCurrentRequest() {
	     RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	     Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
	     Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
	     HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
	     Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
	     return servletRequest;
	 }
	 
	 public UserDTO getUserDTO() {
    	String authorization = getCurrentRequest().getHeader("Authorization");
    	String token = authorization.replace("Bearer ", "");
    	if(tokenService.tokenEncontrada(token)) {
    		DecodedJWT jwt = JWT.decode(token);    	    
    	    byte[] decodedBytes = Base64.getDecoder().decode(jwt.getPayload());
    	    String decodedString = new String(decodedBytes);
    	    UserDTO dto = new Gson().fromJson(decodedString, UserDTO.class);
    		return dto;
    	};
		 return null;
	 }
	 
	 
}
