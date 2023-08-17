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
import com.hormiguero.reina.service.HormigueroUris;
import com.hormiguero.reina.service.HormigueroUris.SubSistemas;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ReinaApplicationTests {

	@Autowired
	public HormigaController instance;
	
	private MockMvc _mock;
	
	private boolean entornoStatus;
	private boolean comidaStatus;
	
	@BeforeEach
	public void initializeApp() {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://queen:reina2023reina@hormigareina.twxfm7c.mongodb.net/hormiguero?retryWrites=true&w=majority");
		this._mock = standaloneSetup(instance)
				.build();
	}
	

	
	@Test
	@Order(0)
	public void testFoodCost()  throws Exception {
		try {
			this._mock.perform(get("/v1/entorno/foodCost"))
				.andExpect(MockMvcResultMatchers.status().isOk());
			this.entornoStatus = true;
		} catch (Exception ex) {
			this.entornoStatus = false;
			throw ex;
		}
	}
	
	@Test
	@Order(1)
	public void testFoodAvailable()  throws Exception {
		try {
			this._mock.perform(get("/v1/comida/foodAvailable"))
				.andExpect(MockMvcResultMatchers.status().isOk());
			this.comidaStatus = true;
		} catch (Exception ex) {
			this.comidaStatus = false;
			throw ex;
		}
	}

	@Test
	@Order(5)
	public void testNuevaHormiga() throws Exception {
		if ( !this.entornoStatus)
			throw new Exception("Imposible crear nuevas hormigas cuando el Subsistema de Entorno esta caido. Endpoint: " + HormigueroUris.getInstance().getUrl(SubSistemas.ENTORNO));
		if ( !this.comidaStatus)
			throw new Exception("Imposible crear nuevas hormigas cuando el Subsistema de Comida esta caido. Endpoint: " + HormigueroUris.getInstance().getUrl(SubSistemas.COMIDA));
		
		String tipo = "COMPILE_TEST";
		int[] values = {11,13};
		String[] bodies = new String[values.length];
		for (int i = 0; i < values.length; i++ ) {
			ResultActions response = this._mock.perform(get("/v1/getHormiga?cantidad={0}&tipo={1}", values[i], tipo)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk());
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
	@Order(10)
	public void testReleaseHormiga() throws Exception {
		this._mock.perform(post("/v1/releaseHormiga").content(this.testHormigas("RELEASE")).header("Content-Type", "application/JSON"));
		//.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(20)
	public void testKillHormiga() throws Exception {
		this._mock.perform(post("/v1/killHormiga").content(this.testHormigas("DELETE")).header("Content-Type", "application/JSON"));
		//.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(30)
	public void testListtAll() throws Exception {
		this._mock.perform(get("/v1/listAll"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(40)
	public void testSwagger() throws Exception {
		this._mock.perform(get("/swagger"))
		.andExpect(MockMvcResultMatchers.status().isFound());
	}
	
	private String testHormigas(String tipo) throws Exception {
		if ( !this.comidaStatus) {
			throw new Exception("Subsistema de Comida esta caido");
		}
		if ( !this.entornoStatus) {
			throw new Exception("Subsistema de Entorno esta caido");
		}
		ResultActions response = this._mock.perform(get("/v1/getHormiga?cantidad=15&tipo={0}", tipo).accept(MediaType.APPLICATION_JSON));
		return response.andReturn().getResponse().getContentAsString();
	}
}