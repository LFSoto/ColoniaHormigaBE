package com.hormiguero.reina.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hormiguero.reina.entity.HormigaEntity;
import com.hormiguero.reina.service.EntornoService;
import com.hormiguero.reina.service.ReinaService;

@RestController
public class HormigaController {

	@Autowired
    private ReinaService reina;
	
	@Autowired
	private EntornoService entorno;
    


	/**
	 * Consulta y devuelve todas las hormigas encontradas en la base de datos
	 * @param cantidad - Cantidad de hormigas requeridas
	 * @param tipo - Tipo de hormiga requerida
	 * @return Lista conteniendo las hormigas encontradas
	 */
    @GetMapping("/v1/getHormiga")
    public List<HormigaEntity> getHormiga(@RequestParam int cantidad, @RequestParam String tipo) {
    	return reina.getHormigas(cantidad, tipo);
    }

    /**
     * Libera hormigas para ponerlas a disposición
     * @param hormigas - Lista de hormigas a liberar
     */
    @PostMapping(value = "/v1/releaseHormiga", consumes = {"application/JSON"})
    public void releaseHormigas(@RequestBody HormigaEntity[] hormigas) {
    	reina.releaseHormigas(hormigas);
    }

	/**
	 * Se eliminan hormigas de la base de datos
	 * @param hormigas - Lista de hormigas a eliminar
	 */
    @PostMapping(value = "/v1/killHormiga", consumes = {"application/JSON"})
    public void killHormigas(@RequestBody HormigaEntity[] hormigas) {
		reina.killHormigas(hormigas);
    }
    
    @GetMapping("/v1/testServiceImplementation")
    public String testServiceImplementation() {
    	return this.entorno.testImplementation();
    }
    
    @GetMapping("/v1/listAll")
    public List<HormigaEntity> listAll() {
    	return this.reina.listAll();
    }
}
