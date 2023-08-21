package com.hormiguero.reina.service;

import java.time.Duration;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger log;
	
	public ExternalHormigueroService(RestTemplateBuilder builder) {
		builder.setConnectTimeout(Duration.ofSeconds(3));
		builder.setReadTimeout(Duration.ofSeconds(3));
		this.endpoint = builder.build();
		this.log = LoggerFactory.getLogger(ExternalHormigueroService.class);
		setUrl("");
	}
	
	private void setUrl(String endpoint) {
		this.entornoEndpoint = endpoint;
	}
	
	public int getHormigaCost() {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.ENTORNO));
		int cost = 3;
		if (entornoEndpoint.startsWith("http")) {

			EntornoEntity result = this.endpoint.getForObject(entornoEndpoint, EntornoEntity.class);
			if (result != null) 
				cost = result.getAntCost();
		}
		return cost;
	}
	
	public int getFoodAvailable() {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA));
		int result = 0;
		if (entornoEndpoint.startsWith("http")) {
			result = Integer.parseInt(this.endpoint.getForObject(entornoEndpoint, String.class));
		}
		return result;
	}

	public void getFood(int food)  {
		if (food <= 0) return;
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA) + "?food=" + food);
		if (entornoEndpoint.startsWith("http")) {
			try {
				this.endpoint.put(entornoEndpoint, food);
			} catch (HttpServerErrorException ex) {				
				log.error("[{0:yyyy-MM-ddTHH:mm:ss.000}] [PUT] {1}. Error message: {2}", new Date(), this.entornoEndpoint, ex.getResponseBodyAsString());
			} catch (ResourceAccessException ex) {
				log.error("[{0:yyyy-MM-ddTHH:mm:ss.000}] [PUT] {1}. Error message: {2}", new Date(), this.entornoEndpoint, ex.getMessage());				
			}
			
		}
	}	
}
