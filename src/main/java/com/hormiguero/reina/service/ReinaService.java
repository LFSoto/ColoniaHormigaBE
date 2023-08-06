package com.hormiguero.reina.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hormiguero.reina.entity.HormigaEntity;
import com.hormiguero.reina.repository.HormigaRepository;

@Service
public class ReinaService implements IReinaService {

    @Autowired
    private HormigaRepository hormigaRepository;    

    private HormigaEntity[] addHormigas(int cantidad, int ultimoId, String tipo) {
    	HormigaEntity[] hormigas = new HormigaEntity[cantidad];
        for (int i = 0; i < cantidad; i++) {
            HormigaEntity hormiga = new HormigaEntity();
            hormiga.setId(i + 1 + ultimoId);
            hormiga.setType(tipo);
            hormiga.setDateOfBirth(new Date());
            hormigas[i] = hormiga;

            hormigaRepository.save(hormiga);
        }
        return hormigas;
    }

    public HormigaEntity[] getHormigas(int cantidad, String tipo) {
    	
    	HormigaEntity[] resultado = new HormigaEntity[cantidad];
    	List<HormigaEntity> all = hormigaRepository.findAll();
    	int added = 0;
    	
    	for (int i = 0; i < all.size() && resultado.length < cantidad; i++) {
    		HormigaEntity hormiga = all.get(i);
    		
    		if (hormiga.getType() == null) {
    			
    			hormiga.setDateOfBirth(Date.from(Instant.now()));
    			hormiga.setType(tipo);
    			
    			resultado[added++] = hormiga;
    			hormigaRepository.save(hormiga);
    		}
    	}
    	if (added < cantidad) {
    		int indexId = all.size() > 0 ? (all.get(all.size() - 1)).getId() : 0;
    		for (HormigaEntity newHormiga : addHormigas(cantidad - added, indexId, tipo)) {
    			resultado[added++] = newHormiga;
    		}
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
			hormigaRepository.delete(hormiga);
		}
	}

}
