package com.financas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
public class FinancasApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(FinancasApplication.class, args);
	}

}
