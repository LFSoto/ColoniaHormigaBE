package com.hormiguero.reina;

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
	
	@Test
	@BeforeAll
	void initializeApp() {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://queen:reina2023reina@hormigareina.twxfm7c.mongodb.net/hormiguero?retryWrites=true&w=majority");
		this._mock = standaloneSetup(instance)
				.build();
		
		this.entornoStatus = true;
		this.comidaStatus = true;
		this.mapper = new ObjectMapper();
	}
	
	@Test
	@BeforeEach
	void testEntorno() {
		Assert.isTrue(this.entornoStatus, "Imposible crear nuevas hormigas cuando el Subsistema de Entorno esta caido. Endpoint: " + HormigueroUris.getInstance().getUrl(SubSistemas.ENTORNO));
	}
	
	@Test
	@BeforeEach
	void testComida() {
		Assert.isTrue(this.comidaStatus, "Imposible crear nuevas hormigas cuando el Subsistema de Comida esta caido. Endpoint: " + HormigueroUris.getInstance().getUrl(SubSistemas.COMIDA));
	}

	
	@Test
	@Order(0)
	void testFoodCost()  throws Exception {
		this.entornoStatus = false;
		this._mock.perform(get("/v1/entorno/foodCost"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		this.entornoStatus = true;
	}
	
	@Test
	@Order(1)
	void testFoodAvailable()  throws Exception {
		this.comidaStatus = false;
		this._mock.perform(get("/v1/comida/foodAvailable"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		this.comidaStatus = true;
	}

	@Test
	@Order(5)
	void testNuevaHormiga() throws Exception {
		List<HormigaEntity> tenAnts = getHormiga(10);
		Assert.notEmpty(tenAnts, "No se crearon 10 hormigas iniciales");
		Assert.isTrue(tenAnts.size() == 10, "Se esperaban 10 hormigas, mientras que se encontraron " + tenAnts.size());
		setAnts(getHormiga(10));
	}
	
	@Test
	@Order(6)
	void testNegociarHormiga() throws Exception {

		List<HormigaEntity> negociated = getHormiga(1);
		Assert.isTrue(negociated.addAll(getAnts()), "La nueva hormiga negociada no ha podido ser agregada a la lista existente.");
		setAnts(negociated);
	}

	@Test
	@Order(7)
	void testListtAll() throws Exception {
		byte[] response = this._mock.perform(get("/v1/listAll"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn().getResponse().getContentAsByteArray();
		
		List<HormigaEntity> all = mapper.readValue(response, new TypeReference<List<HormigaEntity>>() {});

		Assert.isTrue(getAnts().size() == all.size(), "La lista total de hormigas devueltas (" + this.ants.size() + ") es diferente de las devueltas por el API (" + all.size() + ").");
	}
	
	@Test
	@Order(10)
	void testReleaseHormiga() throws Exception {
		
		this._mock.perform(post("/v1/releaseHormiga").content(mapper.writeValueAsBytes(getAnts().listIterator(5))).header("Content-Type", "application/JSON"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Order(11)
	void testReasignacionHormiga() throws Exception {
		List<HormigaEntity> reasigned = getHormiga(5);
		Assert.isTrue(reasigned.size() == 5, "Se esperaba tomar 5 hormigas libres, pero se reasignaron apenas " + reasigned.size());
	}
	
	@Test
	@Order(20)
	void testKillHormiga() throws Exception {
		this._mock.perform(post("/v1/killHormiga").content(mapper.writeValueAsBytes(getAnts())).header("Content-Type", "application/JSON"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	@Order(40)
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
	
	private List<HormigaEntity> getHormiga(int cantidad) throws Exception {
		
		String url = "/v1/getHormiga?cantidad={0}&tipo=COMPILE_TEST";
		String response = this._mock.perform(get(url, cantidad)).andReturn().getResponse().getContentAsString();
		Assert.hasLength(response, "La respuesta de " + url + " esta vacía");
		
		List<HormigaEntity> ants = mapper.readValue(response, new TypeReference<List<HormigaEntity>>() {});
		Assert.noNullElements(ants, "No se encontraron hormigas creadas");
		Assert.isTrue(ants.size() == cantidad, "Se esperaba crear " + cantidad + " hormigas, pero se crearon apenas " + ants.size());
		
		return ants;
	}
}