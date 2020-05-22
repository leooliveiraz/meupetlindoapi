package br.com.leorocha.meudoglindo.dto;

import lombok.Data;
@Data
public class SubscriptionDTO {
	private String endpoint;
	private String expirationTime;
	private SubscriptionKeyDTO keys;
	
}
