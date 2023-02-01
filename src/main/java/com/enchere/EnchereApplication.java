package com.enchere;

import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EnchereApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnchereApplication.class, args);
	}

	@Bean
	WebMvcConfigurer corsConfig(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				registry.addMapping("/**").allowedOriginPatterns("*")
						.allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
			}
		};
	}
}
