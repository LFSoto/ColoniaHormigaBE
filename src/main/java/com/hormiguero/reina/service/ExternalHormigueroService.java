package com.hormiguero.reina.service;

import java.util.Date;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.hormiguero.reina.entity.EntornoEntity;

@Service
public class ExternalHormigueroService {

	private final RestTemplate endpoint;
	private String entornoEndpoint;
	private int cacheFood;
	
	public ExternalHormigueroService(RestTemplateBuilder builder) {
		this.endpoint = builder.build();
		setUrl("");
		this.cacheFood = 0;
	}
	
	private void setUrl(String endpoint) {
		this.entornoEndpoint = endpoint;
	}
	
	public int getHormigaCost() throws Exception {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.ENTORNO));
		int cost = 3;
		if (entornoEndpoint.startsWith("http")) {

			//EntornoEntity result = this.endopoint.getForObject(entornoEndpoint, EntornoEntity.class);
			//cost = result.getAntCost();			
		}
		return cost;
	}
	
	public int getFoodAvailable() throws Exception {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA));
		int result = this.cacheFood == 0 ? 10 : this.cacheFood;
		if (entornoEndpoint.startsWith("http")) {
			try {
				result = Integer.parseInt(this.endpoint.getForObject(entornoEndpoint, String.class));
			} catch (ResourceAccessException ex) {
				result = 20;
			}
		}
		this.cacheFood = this.cacheFood == 0 ? result : this.cacheFood;
		return result;
	}

	public void getFood(int food) throws Exception  {
		if (food <= 0) return;
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA) + "?food=" + food);
		if (entornoEndpoint.startsWith("http")) {
			try {
				this.endpoint.put(entornoEndpoint, food);
			} catch (HttpServerErrorException ex) {
				System.out.format("{0:yyyy-MM-ddTHH:mm:ss.000} --- ERROR --- COMIDA --- {1}", new Date(), ex.getResponseBodyAsString());
				return;
			} catch (ResourceAccessException ex) {
				System.out.format("{0:yyyy-MM-ddTHH:mm:ss.000} --- WARNING --- COMIDA --- {1}", new Date(), ex.getMessage());
			}
			
		}
		this.cacheFood -= food;
	}	
}
