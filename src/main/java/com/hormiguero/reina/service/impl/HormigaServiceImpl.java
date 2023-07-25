package com.hormiguero.reina.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hormiguero.reina.entity.HormigaEntity;
import com.hormiguero.reina.repository.HormigaRepository;
import com.hormiguero.reina.service.HormigaService;

public class HormigaServiceImpl implements HormigaService {

    
    private HormigaRepository hormigaRepository;

    public List<HormigaEntity> getHormigas(int cantidad, String tipo) {
        List<HormigaEntity> hormigas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            HormigaEntity hormiga = new HormigaEntity();
            hormiga.setId(i + 1);
            hormiga.setType(tipo);
            hormiga.setDateOfBirth(new Date());
            hormigas.add(hormiga);

            hormigaRepository.save(hormiga);
        }
        return hormigas;
    }

	
	public void returnHormigas(List<HormigaEntity> hormigas) {
        for (HormigaEntity hormiga : hormigas) {
            HormigaEntity existingHormiga = hormigaRepository.findById(hormiga.getId()).orElse(null);
            if (existingHormiga != null) {
                existingHormiga.setType(null);
                hormigaRepository.save(existingHormiga);
            }
        }
		
	}


	
	public void killHormigas(List<HormigaEntity> hormigas) {
        hormigaRepository.deleteAll(hormigas);
	}

}
