package com.hormiguero.reina.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hormiguero.reina.entity.EntornoEntity;

@Service
public class ExternalHormigueroService {

	private final RestTemplate endopoint;
	private String entornoEndpoint;
	
	public ExternalHormigueroService(RestTemplateBuilder builder) {
		this.endopoint = builder.build();
		setUrl("");
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
		int result = 10;
		if (entornoEndpoint.startsWith("http")) {
			result = Integer.parseInt(this.endopoint.getForObject(entornoEndpoint, String.class));
			
		}
		return result;
	}

	public boolean getFood(int food) throws Exception  {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA));
		if (entornoEndpoint.startsWith("http")) {
			this.endopoint.put(entornoEndpoint, food);
			return true;
		}		
		return false;
	}	
}
