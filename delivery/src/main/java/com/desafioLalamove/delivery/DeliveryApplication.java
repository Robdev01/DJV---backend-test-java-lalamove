package com.desafioLalamove.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicacao.
 *
 * O @SpringBootApplication concentra tres comportamentos do Spring Boot:
 * - registra essa classe como ponto de configuracao;
 * - habilita configuracao automatica;
 * - faz o scan de componentes do pacote e subpacotes.
 */
@SpringBootApplication
public class DeliveryApplication {

	/**
	 * Ponto de entrada da aplicacao Java.
	 *
	 * Quando esse metodo e executado, o Spring sobe o contexto da aplicacao,
	 * inicializa beans e inicia o servidor embarcado.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}

}
