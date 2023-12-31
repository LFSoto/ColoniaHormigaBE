package com.hormiguero.reina.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hormiguero.reina.entity.HormigaEntity;
import com.hormiguero.reina.repository.HormigaRepository;

@Service
public class ReinaService implements IReinaService {
	
	private static final int FREE_ANTS = 10;

    @Autowired
    private HormigaRepository hormigaRepository;
    
    @Autowired
    private ExternalHormigueroService hormigueroEndpoint;

    private HormigaEntity addHormiga(String tipo) {
    	
    	List<HormigaEntity> all = hormigaRepository.findAll();
		int indexId = all.isEmpty() ? 0 : (all.get(all.size() - 1)).getId();
		
    	HormigaEntity hormiga = new HormigaEntity();
        hormiga.setId(indexId + 1);
        hormiga.setType(tipo);
        hormiga.setBirthday(new Date());

        hormigaRepository.save(hormiga);
        return hormiga;
    }
    
    
    private void negociateHormigas(int cantidad, String tipo, List<HormigaEntity> all, List<HormigaEntity> reservadas) {
    	
    	while (all.size() < FREE_ANTS && cantidad > 0) {
    		
    		reservadas.add( addHormiga(tipo) );
    		cantidad--;
    		all = hormigaRepository.findAll();
    	}
    	
    	if (cantidad > 0 && all.size() >= FREE_ANTS) {
        	int foodAvailable = hormigueroEndpoint.getFoodAvailable();
        	int costPerAnt = hormigueroEndpoint.getHormigaCost();
        	int cost = cantidad * costPerAnt;
        	if (foodAvailable < cost) {
        		cantidad = foodAvailable / costPerAnt;
        	}
        	while (cantidad > 0) {
            	this.hormigueroEndpoint.getFood(costPerAnt);
        		reservadas.add( addHormiga(tipo) );
        		cantidad--;
        	}
    	}
    }

    public List<HormigaEntity> getHormigas(int cantidad, String tipo) throws Exception {
    	
    	List<HormigaEntity> resultado = new ArrayList<>();
    	List<HormigaEntity> all = hormigaRepository.findAll();
    	
    	for (int i = 0; i < all.size() && cantidad > 0; i++) {
    		HormigaEntity hormiga = all.get(i);
    		
    		if (hormiga.getType().compareTo("FREE") == 0) {
    			
    			hormiga.setBirthday(Date.from(Instant.now()));
    			hormiga.setType(tipo);
    			
    			resultado.add(hormiga);
    			hormigaRepository.save(hormiga);
    			cantidad--;
    		}
    	}
    	if (cantidad > 0) {
    		negociateHormigas(cantidad, tipo, all, resultado);    		
    	}
    	return resultado;
    }
	
	public void releaseHormigas(HormigaEntity[] hormigas) {
        for (HormigaEntity hormiga : hormigas) {
            HormigaEntity existingHormiga = hormigaRepository.findById(hormiga.getId()).orElse(null);
            if (existingHormiga != null) {
                existingHormiga.setType("FREE");
                hormigaRepository.save(existingHormiga);
            }
        }		
	}
	
	public void killHormigas(HormigaEntity[] hormigas) {
		for (HormigaEntity hormiga : hormigas) {
			HormigaEntity existingHormiga = hormigaRepository.findById(hormiga.getId()).orElse(null);
			if (existingHormiga != null) {
				hormigaRepository.delete(existingHormiga);
			}
		}
	}
	
	public List<HormigaEntity> listAll() {		
		return hormigaRepository.findAll();
	}

}
