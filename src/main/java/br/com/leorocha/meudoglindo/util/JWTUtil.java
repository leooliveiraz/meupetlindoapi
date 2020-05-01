package br.com.leorocha.meudoglindo.util;

import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
	public static String converterTokenParaObjeto(String token) {
    	    DecodedJWT jwt = JWT.decode(token);
    	    byte[] decodedBytes = Base64.getDecoder().decode(jwt.getPayload());
    	    String decodedString = new String(decodedBytes);
    	    return decodedString;    	
	}
}
