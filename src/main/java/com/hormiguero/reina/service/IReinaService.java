package com.hormiguero.reina.service;

import java.util.List;

import  com.hormiguero.reina.entity.HormigaEntity;

public interface IReinaService {

    public List<HormigaEntity> getHormigas(int cantidad, String tipo) throws Exception;

	public void releaseHormigas(HormigaEntity[] hormigas);

	public void killHormigas(HormigaEntity[] hormigas);
	
	public List<HormigaEntity> listAll();
}
