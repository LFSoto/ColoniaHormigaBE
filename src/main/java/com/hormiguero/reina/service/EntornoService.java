package com.hormiguero.reina.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EntornoService {

	private final RestTemplate endopoint;
	private final String entornoEndpoint = "";
	
	public EntornoService(RestTemplateBuilder builder) {
		this.endopoint = builder.build();
	}
	
	public int getHormigasGratis() {
		if (entornoEndpoint.matches("^http")) {
			String response = this.endopoint.getForObject(entornoEndpoint, String.class);
		
			return Integer.parseInt(response);
		}
		return 10;
	}
	
	public String testImplementation() {
		return this.endopoint.getForObject("https://jsonplaceholder.typicode.com/posts", String.class);
	}
}
