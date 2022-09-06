package com.malbano.rural.model.repository;

import com.malbano.rural.model.entity.DadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DadosRepository extends PagingAndSortingRepository<DadosEntity, Long> {
}
