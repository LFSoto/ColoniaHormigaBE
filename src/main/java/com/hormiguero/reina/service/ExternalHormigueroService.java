package com.hormiguero.reina.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hormiguero.reina.entity.EntornoEntity;
import com.hormiguero.reina.service.HormigueroUris.SubSistemas;

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
	
	public int getHormigaCost() {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.ENTORNO));
		if (entornoEndpoint.matches("^http.*")) {
			try {
				return this.endopoint.getForObject(entornoEndpoint, EntornoEntity.class).getAntCost();
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}
	
	public int getFoodAvailable() {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA));
		if (entornoEndpoint.matches("^http.*")) {
			try {
				return Integer.parseInt(this.endopoint.getForObject(entornoEndpoint, String.class));
			} catch (Exception e) {
				return -1;
			}
		}
		return 0;
	}

	public boolean getFood(int food) {
		setUrl(HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA));
		if (entornoEndpoint.matches("^http.*")) {
			try {
				this.endopoint.put(entornoEndpoint, food);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		return false;
	}	
}
