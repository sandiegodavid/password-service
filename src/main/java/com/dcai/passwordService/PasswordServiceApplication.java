package com.dcai.passwordService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class PasswordServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordServiceApplication.class, args);
	}
}
