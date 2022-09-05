package com.malbano.rural.model.repository;

import com.malbano.rural.model.entity.DadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosRepository extends JpaRepository<DadosEntity, Long> {
}
