package com.hormiguero.reina;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import com.hormiguero.reina.controller.HormigaController;
import com.hormiguero.reina.entity.HormigaEntity;
import com.hormiguero.reina.service.HormigueroUris;
import com.hormiguero.reina.service.HormigueroUris.SubSistemas;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class ReinaApplicationTests {

	@Autowired
	private HormigaController instance;	
	private MockMvc _mock;
	private ObjectMapper mapper;
	private List<HormigaEntity> ants;
	private boolean entornoStatus;
	private boolean comidaStatus;
	
	@BeforeAll
	void initializeApp() {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://queen:reina2023reina@hormigareina.twxfm7c.mongodb.net/hormiguero?retryWrites=true&w=majority");
		this._mock = standaloneSetup(instance)
				.build();
		
		this.entornoStatus = true;
		this.comidaStatus = true;
		this.mapper = new ObjectMapper();
		try {
			byte[] response = this._mock.perform(get("/v1/listAll"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsByteArray();
			
			List<HormigaEntity> found = mapper.readValue(response, new TypeReference<List<HormigaEntity>>() {});
			if (!found.isEmpty()) {

				this._mock.perform(post("/v1/killHormiga").content(mapper.writeValueAsBytes(found)).header("Content-Type", "application/JSON"))
					.andExpect(MockMvcResultMatchers.status().isOk());
			}
		} catch (Exception ex) {
		}
		setAnts(new ArrayList<HormigaEntity>());
	}
	
	@BeforeEach
	void testEntornoOnline() {
		Assert.isTrue(this.entornoStatus, "Imposible crear nuevas hormigas cuando el Subsistema de Entorno esta caido. Endpoint: " + HormigueroUris.getInstance().getUrl(SubSistemas.ENTORNO));
	}
	
	@BeforeEach
	void testComidaOnline() {
		Assert.isTrue(this.comidaStatus, "Imposible crear nuevas hormigas cuando el Subsistema de Comida esta caido. Endpoint: " + HormigueroUris.getInstance().getUrl(SubSistemas.COMIDA));
	}

	
	@Test
	@Order(10)
	void testFoodCost()  throws Exception {
		this.entornoStatus = false;
		this._mock.perform(get("/v1/entorno/foodCost"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		this.entornoStatus = true;
	}
	
	@Test
	@Order(20)
	void testFoodAvailable()  throws Exception {
		this.comidaStatus = false;
		this._mock.perform(get("/v1/comida/foodAvailable"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		this.comidaStatus = true;
	}

	@Test
	@Order(30)
	void testNuevaHormiga() throws Exception {

		String url = "/v1/getHormiga?cantidad={0}&tipo=TESTING";
		String response = this._mock.perform(get(url, 10))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.hasLength(response, "La respuesta de " + url + " esta vacía");
		
		List<HormigaEntity> tenAnts = mapper.readValue(response, new TypeReference<List<HormigaEntity>>() {});
		Assert.notEmpty(tenAnts, "No se crearon 10 hormigas iniciales");
		Assert.isTrue(tenAnts.size() == 10, "Se esperaban 10 hormigas, mientras que se encontraron " + tenAnts.size());
		setAnts(tenAnts);
	}
	
	@Test
	@Order(40)
	void testNegociarHormiga() throws Exception {

		String url = "/v1/getHormiga?cantidad={0}&tipo=TESTING";
		String response = this._mock.perform(get(url, 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.hasLength(response, "La respuesta de " + url + " esta vacía");
		
		List<HormigaEntity> negociated = mapper.readValue(response, new TypeReference<List<HormigaEntity>>() {});
		Assert.isTrue(negociated.addAll(getAnts()), "La nueva hormiga negociada no ha podido ser agregada a la lista existente.");
		setAnts(negociated);
	}

	@Test
	@Order(50)
	void testListtAll() throws Exception {
		byte[] response = this._mock.perform(get("/v1/listAll"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn().getResponse().getContentAsByteArray();
		
		List<HormigaEntity> all = mapper.readValue(response, new TypeReference<List<HormigaEntity>>() {});

		Assert.isTrue(getAnts().size() == all.size(), "La lista total de hormigas devueltas (" + this.ants.size() + ") es diferente de las devueltas por el API (" + all.size() + ").");
	}
	
	@Test
	@Order(60)
	void testReleaseHormiga() throws Exception {
		
		this._mock.perform(post("/v1/releaseHormiga").content(mapper.writeValueAsBytes(getAnts())).header("Content-Type", "application/JSON"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(70)
	void testReasignacionHormiga() throws Exception {
		
		String url = "/v1/getHormiga?cantidad={0}&tipo=TESTING";
		String response = this._mock.perform(get(url, 5))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.hasLength(response, "La respuesta de " + url + " esta vacía");
		
		List<HormigaEntity> reasigned = mapper.readValue(response, new TypeReference<List<HormigaEntity>>() {});
		Assert.isTrue(reasigned.size() == 5, "Se esperaba tomar 5 hormigas libres, pero se reasignaron apenas " + reasigned.size());
	}
	
	@Test
	@Order(80)
	void testKillHormiga() throws Exception {
		this._mock.perform(post("/v1/killHormiga").content(mapper.writeValueAsBytes(getAnts())).header("Content-Type", "application/JSON"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	@Order(90)
	void testSwagger() throws Exception {
		this._mock.perform(get("/swagger"))
			.andExpect(MockMvcResultMatchers.status().isFound());
	}
	
	private List<HormigaEntity> getAnts() {
		return this.ants;
	}
	
	private void setAnts(List<HormigaEntity> ants) {
		this.ants = ants;
	}
}