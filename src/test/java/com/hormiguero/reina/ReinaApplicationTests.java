package com.hormiguero.reina;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import com.hormiguero.reina.controller.HormigaController;

@SpringBootTest
class ReinaApplicationTests {

	private MockMvc _mock;
	
	@BeforeEach
	public void initializeApp() {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://queen:reina2023reina@hormigareina.twxfm7c.mongodb.net/hormiguero?retryWrites=true&w=majority");
		this._mock = standaloneSetup(new HormigaController())
				.alwaysExpect(status().isOk())
				.alwaysExpect(content().contentType("application/json;charset=UTF-8"))
				.build();
	}

	@Test
	public void testNuevaHormiga() {
		int cantidad = 2;
		String tipo = "D";
		String body;
		try {
			ResultActions response = this._mock.perform(get("/v1/getHormiga?cantidad=2&tipo=D", cantidad, tipo).accept(MediaType.APPLICATION_JSON));
			body = response.andReturn().getResponse().getContentAsString();
			Assert.hasLength(body, "La lista retornada por el servicio esta vacía");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
