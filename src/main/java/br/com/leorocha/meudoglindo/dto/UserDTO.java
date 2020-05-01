package br.com.leorocha.meudoglindo.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String iss;
	private String azp;
	private String aud;
	private String sub;
	private String email;
	private boolean email_verified;
	private String at_hash;
	private String name;
	private String picture;
	private String given_name;
	private String family_name;
	private String locale;
	private int iat;
	private int exp;
	private String jti;
}
