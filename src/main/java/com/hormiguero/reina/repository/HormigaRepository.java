package com.hormiguero.reina.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hormiguero.reina.entity.HormigaEntity;

public interface HormigaRepository extends MongoRepository<HormigaEntity, Integer> {
    List<HormigaEntity> findByTypeIsNull();

}