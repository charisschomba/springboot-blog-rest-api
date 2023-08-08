package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "SpringBoot Blog App APIs",
				description = "SpringBoot Blog App APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Chariss",
						email = "charisschomba@gmail/com"
				),
				license = @License(
						name = "MIT"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Source Code",
				url = "https://github.com/charisschomba/springboot-blog-rest-api"
		)
)
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
