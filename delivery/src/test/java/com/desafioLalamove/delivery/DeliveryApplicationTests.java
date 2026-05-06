package com.desafioLalamove.delivery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de integracao da aplicacao.
 *
 * Esses testes sobem o contexto do Spring e verificam se recursos
 * importantes, como o Swagger, estao ativos.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DeliveryApplicationTests {

	// MockMvc permite chamar endpoints sem subir um servidor HTTP real.
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		// Se a aplicacao sobe sem falhar, esse teste passa.
	}

	@Test
	@DisplayName("Swagger deve expor o OpenAPI em /v3/api-docs")
	void shouldExposeOpenApiDocs() throws Exception {
		// Verifica se o endpoint JSON da documentacao esta disponivel.
		mockMvc.perform(get("/v3/api-docs"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"openapi\"")));
	}

	@Test
	@DisplayName("Swagger UI deve estar disponivel")
	void shouldExposeSwaggerUi() throws Exception {
		// O caminho /swagger-ui.html redireciona para a interface HTML real.
		mockMvc.perform(get("/swagger-ui.html"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", containsString("/swagger-ui/index.html")));
	}

}
