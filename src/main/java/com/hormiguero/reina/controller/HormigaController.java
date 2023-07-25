package com.hormiguero.reina.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hormiguero.reina.service.HormigaService;
import com.hormiguero.reina.entity.HormigaEntity;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class HormigaController {

    @Autowired
    private HormigaService hormigaService;


    @GetMapping("/v1/getHormiga")
    @ApiOperation("Get Hormigas")
    public List<HormigaEntity> getHormiga(@RequestParam int cantidad, @RequestParam String tipo) {
        return hormigaService.getHormigas(cantidad, tipo);
    }
    
    @PostMapping("/v1/returnHormigas")
    @ApiOperation("Return Hormigas")
    public void returnHormigas(@RequestBody List<HormigaEntity> hormigas) {
        hormigaService.returnHormigas(hormigas);
    }
    
    @PostMapping("/v1/killHormigas")
    @ApiOperation("Kill Hormigas")
    public void killHormigas(@RequestBody List<HormigaEntity> hormigas) {
        hormigaService.killHormigas(hormigas);
    }
    
}
