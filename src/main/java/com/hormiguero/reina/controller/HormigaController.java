package com.hormiguero.reina.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hormiguero.reina.entity.HormigaEntity;
import com.hormiguero.reina.repository.HormigaRepository;


@RestController
public class HormigaController {

	@Autowired
    private HormigaRepository repo;
    

    private List<HormigaEntity> addHormigas(int cantidad, String tipo) {
        List<HormigaEntity> hormigas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            HormigaEntity hormiga = new HormigaEntity();
            hormiga.setId(i + 1);
            hormiga.setType(tipo);
            hormiga.setDateOfBirth(new Date());
            hormigas.add(hormiga);

            repo.save(hormiga);
        }
        return hormigas;
    }

	/**
	 * Consulta y devuelve todas las hormigas encontradas en la base de datos
	 * @param cantidad - Cantidad de hormigas requeridas
	 * @param tipo - Tipo de hormiga requerida
	 * @return Lista conteniendo las hormigas encontradas
	 */
    @GetMapping("/v1/getHormiga")
    @ApiOperation("Get Hormigas")
    public List<HormigaEntity> getHormiga(@RequestParam int cantidad, @RequestParam String tipo) {
    	
    	List<HormigaEntity> resultado = new ArrayList<HormigaEntity>();
    	List<HormigaEntity> all = repo.findAll();
    	
    	for (int i = 0; i < all.size() && resultado.size() < cantidad; i++) {
    		HormigaEntity hormiga = all.get(i);
    		
    		if (hormiga.getType() == null || hormiga.isSameType(tipo) ) {
    			resultado.add(hormiga);
    		}
    	}
    	if (resultado.size() < cantidad) {
    		resultado.addAll(addHormigas(cantidad - resultado.size(), tipo));
    	}
    	return resultado;
    }

    /**
     * Libera hormigas para ponerlas a disposición
     * @param cantidad - Cantidad de hormigas a liberar
     * @param tipo - Tipo de hormiga a liberar
     */
    @PostMapping("/v1/releaseHormiga")
    @ApiOperation("Return Hormigas")
    public void returnHormigas(@RequestParam int cantidad, @RequestParam String tipo) {
		List<HormigaEntity> hormigas = repo.findAll();
		int released = 0;
		
		for (int i = 0; i < hormigas.size() && released < cantidad; i++) {
			HormigaEntity hormiga = hormigas.get(i); 
			
            if (hormiga.getType() != null && hormiga.isSameType(tipo)) {
                hormiga.setType(null);
                repo.save(hormiga);
                released++;
            }
        }
    }

	/**
	 * Se eliminan hormigas de la base de datos
	 * @param cantidad - Cantida de hormigas a eliminar
	 * @param tipo - Tipo de hormiga a eliminar
	 */
    @PostMapping("/v1/killHormiga")
    @ApiOperation("Kill Hormigas")
    public void killHormigas(@RequestParam int cantidad, @RequestParam String tipo) {
		List<HormigaEntity> hormigas = repo.findAll();
		int deleted = 0;
		
		for (int i = 0; i < hormigas.size() && deleted < cantidad; i++) {
			HormigaEntity hormiga = hormigas.get(i);
			if (hormiga.getType() != null && hormiga.isSameType(tipo)) {
				repo.delete(hormiga);
			}
		}
    }
    
}
