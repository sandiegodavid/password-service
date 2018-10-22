package com.dcai.passwordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.dcai.passwordService.mapper.UserSerializer;
import com.dcai.passwordService.model.User;

@SpringBootApplication
@EnableOAuth2Sso
public class PasswordServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordServiceApplication.class, args);
	}

	@Autowired
	private UserSerializer userSerializer;

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer addCustomUserSerialization() {
		return new Jackson2ObjectMapperBuilderCustomizer() {

			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
				jacksonObjectMapperBuilder.serializerByType(User.class, userSerializer);
			}

		};
	}

}
