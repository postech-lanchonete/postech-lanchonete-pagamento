package br.com.postech_lanchonete_pagamento;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagamentoApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Lanchonete do Bairro - Pagamento")
						.description("API para gerenciamento de pagamentos de pedidos de uma lanchonete")
						.license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"))
						.version("1.0.0-rc.01"));
	}
}
