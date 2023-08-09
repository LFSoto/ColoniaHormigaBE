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
	
	private final int FREE = 10;

    @Autowired
    private HormigaRepository hormigaRepository;
    
    @Autowired
    private ExternalHormigueroService hormigueroEndpoint;

    private HormigaEntity[] addHormigas(int cantidad, int ultimoId, String tipo) {
    	HormigaEntity[] hormigas = new HormigaEntity[cantidad];
        for (int i = 0; i < cantidad; i++) {
            HormigaEntity hormiga = new HormigaEntity();
            hormiga.setId(i + 1 + ultimoId);
            hormiga.setType(tipo);
            hormiga.setBirthday(new Date());
            hormigas[i] = hormiga;

            hormigaRepository.save(hormiga);
        }
        return hormigas;
    }
    
    private void negociateHormigas(int cantidad, String tipo, List<HormigaEntity> all, List<HormigaEntity> reservadas) {
    	if (all.size() > FREE) {
        	int costPerAnt = hormigueroEndpoint.getHormigaCost();
        	int foodAvailable = hormigueroEndpoint.getFoodAvailable();
        	int cost = cantidad * costPerAnt;
        	if (cost > foodAvailable) {
        		
        	}
    	}
		int indexId = all.size() > 0 ? (all.get(all.size() - 1)).getId() : 0;
		for (HormigaEntity newHormiga : addHormigas(cantidad, indexId, tipo)) {
			reservadas.add(newHormiga);
		}
    }

    public List<HormigaEntity> getHormigas(int cantidad, String tipo) {
    	
    	List<HormigaEntity> resultado = new ArrayList<HormigaEntity>();
    	List<HormigaEntity> all = hormigaRepository.findAll();
    	
    	for (int i = 0; i < all.size() && cantidad > 0; i++) {
    		HormigaEntity hormiga = all.get(i);
    		
    		if (hormiga.getType() == null) {
    			
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
                existingHormiga.setType(null);
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
