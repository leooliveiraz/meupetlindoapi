package br.com.leorocha.meudoglindo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeudoglindoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeudoglindoApplication.class, args);
		System.out.println("O APP subiu!");
	}

}
