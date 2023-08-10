package com.hormiguero.reina;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import com.hormiguero.reina.controller.HormigaController;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ReinaApplicationTests {

	@Autowired
	public HormigaController instance;
	
	private MockMvc _mock;
	
	@BeforeEach
	public void initializeApp() {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://queen:reina2023reina@hormigareina.twxfm7c.mongodb.net/hormiguero?retryWrites=true&w=majority");
		this._mock = standaloneSetup(instance)
				.build();
	}

	@Test
	@Order(0)
	public void testNuevaHormiga() throws Exception {
		String tipo = "COMPILE_TEST";
		int[] values = {11,22,33};
		String[] bodies = new String[values.length];
		for (int i = 0; i < values.length; i++ ) {
			ResultActions response = this._mock.perform(get("/v1/getHormiga?cantidad={0}&tipo={1}", values[i], tipo).accept(MediaType.APPLICATION_JSON));
					//.andExpect(MockMvcResultMatchers.status().isOk());
			String body = response.andReturn().getResponse().getContentAsString();
			Assert.hasLength(body, "La lista retornada por el servicio esta vacía");
			
			bodies[i] = body;
		}

		for (String body : bodies) {
			this._mock.perform(post("/v1/killHormiga").content(body).header("Content-Type", "application/JSON"))
			.andExpect(MockMvcResultMatchers.status().isOk());	
		}
	}
	
	@Test
	@Order(1)
	public void testReleaseHormiga() throws Exception {
		this._mock.perform(post("/v1/releaseHormiga").content(this.testHormigas("RELEASE")).header("Content-Type", "application/JSON"));
		//.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(2)
	public void testKillHormiga() throws Exception {
		this._mock.perform(post("/v1/killHormiga").content(this.testHormigas("DELETE")).header("Content-Type", "application/JSON"));
		//.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(3)
	public void testListtAll() throws Exception {
		this._mock.perform(get("/v1/listAll"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(4)
	public void testFoodCost()  throws Exception {
		this._mock.perform(get("/v1/entorno/foodCost"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(5)
	public void testFoodAvailable()  throws Exception {
		assertNotEquals(-1, this._mock.perform(get("/v1/comida/foodAvailable")));
	}
	
	@Test
	@Order(6)
	public void testSwagger() throws Exception {
		this._mock.perform(get("/swagger"))
		.andExpect(MockMvcResultMatchers.status().isFound());
	}
	
	private String testHormigas(String tipo) throws Exception {

		ResultActions response = this._mock.perform(get("/v1/getHormiga?cantidad=15&tipo={0}", tipo).accept(MediaType.APPLICATION_JSON));
		return response.andReturn().getResponse().getContentAsString();
	}
}