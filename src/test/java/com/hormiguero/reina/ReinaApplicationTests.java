package com.hormiguero.reina;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hormiguero.reina.controller.HormigaController;
import com.hormiguero.reina.entity.HormigaEntity;

@SpringBootTest
public class ReinaApplicationTests {

	@Autowired
	public HormigaController instance;
	
	private MockMvc _mock;
	private ObjectMapper mapper = new ObjectMapper();
	
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
		this._mock.perform(post("/v1/releaseHormiga").content(this.testHormigas()).header("Content-Type", "application/JSON"));
	}
	
	@Test
	public void testKillHormiga() throws Exception {
		this._mock.perform(post("/v1/killHormiga").content(this.testHormigas()).header("Content-Type", "application/JSON"));
	}
	
	@Test
	public void testConsumeEndpointImplementation()  throws Exception {
		this._mock.perform(get("/v1/testServiceImplementation"));
	}
	
	public String testHormigas() throws JsonProcessingException {
		HormigaEntity[] hormigas = new HormigaEntity[10];
		for (int id = 0; id < hormigas.length; id++) {
			hormigas[id] = new HormigaEntity(id + 1, "TEST", new Date());
		}
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hormigas);
	}
}