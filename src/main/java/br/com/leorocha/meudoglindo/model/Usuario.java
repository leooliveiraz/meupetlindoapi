package br.com.leorocha.meudoglindo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.leorocha.meudoglindo.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true, nullable=false) 
	private String sub;
	private String email;
	private boolean email_verified;
	private String at_hash;
	private String name;
	private String picture;
	private String given_name;
	private String family_name;
	private String locale;
	private String tipo;
	
	public Usuario(UserDTO dto){
		this.setSub(dto.getSub());
		this.setEmail(dto.getEmail());
		this.setEmail_verified(dto.isEmail_verified());
		this.setAt_hash(dto.getAt_hash());
		this.setName(dto.getName());
		this.setPicture(dto.getPicture());
		this.setGiven_name(dto.getGiven_name());
		this.setFamily_name(dto.getFamily_name());
		this.setLocale(dto.getLocale());
		this.setTipo(dto.getIss().contains("google") ? "GOOGLE" : "N");
	}

}
