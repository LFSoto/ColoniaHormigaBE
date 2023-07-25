package com.hormiguero.reina.service;
import java.util.List;

import  com.hormiguero.reina.entity.HormigaEntity;

public interface HormigaService {
    public List<HormigaEntity> getHormigas(int cantidad, String tipo);

	public void returnHormigas(List<HormigaEntity> hormigas);

	public void killHormigas(List<HormigaEntity> hormigas);
}
