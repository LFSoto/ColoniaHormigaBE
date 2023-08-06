package com.hormiguero.reina.service;

import  com.hormiguero.reina.entity.HormigaEntity;

public interface IReinaService {
    public HormigaEntity[] getHormigas(int cantidad, String tipo);

	public void releaseHormigas(HormigaEntity[] hormigas);

	public void killHormigas(HormigaEntity[] hormigas);
}
