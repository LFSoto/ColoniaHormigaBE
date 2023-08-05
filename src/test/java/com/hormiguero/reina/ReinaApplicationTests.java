package com.hormiguero.reina;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import com.hormiguero.reina.controller.HormigaController;

@SpringBootTest
public class ReinaApplicationTests {

	@Autowired
	public HormigaController instance;
	private MockMvc _mock;
	
	@BeforeEach
	public void initializeApp() {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://queen:reina2023reina@hormigareina.twxfm7c.mongodb.net/hormiguero?retryWrites=true&w=majority");
		this._mock = standaloneSetup(instance)
				.alwaysExpect(status().isOk())
				.build();
	}

	@Test
	public void testNuevaHormiga() throws Exception {
		int cantidad = 10;
		String tipo = "TEST";
		
		ResultActions response = this._mock.perform(get("/v1/getHormiga?cantidad={0}&tipo={1}", cantidad, tipo).accept(MediaType.APPLICATION_JSON));
		String body = response.andReturn().getResponse().getContentAsString();
		Assert.hasLength(body, "La lista retornada por el servicio esta vacía");
	

	}
	
	@Test
	public void testReleaseHormiga() throws Exception {
		int cantidad = 1;
		String tipo = "D";
		this._mock.perform(post("/v1/releaseHormiga?cantidad={0}&tipo={1}", cantidad, tipo));
	}
	
	@Test
	public void testKillHormiga() throws Exception {
		int cantidad = 2;
		String tipo = "D";
		this._mock.perform(post("/v1/killHormiga?cantidad={0}&tipo={1}", cantidad, tipo));
	}
}
