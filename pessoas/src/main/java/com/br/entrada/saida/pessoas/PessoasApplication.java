package com.br.entrada.saida.pessoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = { @Server(url = "/",description = "Default server URL")}) // aponta para o contexto que o a aplicacao está rodando
@SpringBootApplication
public class PessoasApplication {

	public static void main(String[] args) {
		SpringApplication.run(PessoasApplication.class, args);
	}

}
